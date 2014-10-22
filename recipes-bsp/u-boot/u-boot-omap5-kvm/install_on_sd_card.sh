#! /bin/sh
# Script to create SD card for OMAP5 uevm.
#
# 22.10.2014: Adapted to be integrated in meta-kvm-arm by Geoffrey Papaux
#
# Author: Brijesh Singh, Texas Instruments Inc.
#       : Adapted for omap5-uevm by Nikhil Devshatwar, Texas Instruments Inc.
#
# Licensed under terms of GPLv2
#

VERSION="0.1"

execute ()
{
    $* >/dev/null
    if [ $? -ne 0 ]; then
        echo
        echo "ERROR: executing $*"
        echo
        exit 1
    fi
}

version ()
{
  echo
  echo "`basename $1` version $VERSION"
  echo "Script to create bootable SD card for omap5-uevm"
  echo

  exit 0
}

usage ()
{
  echo "
Usage: `basename $1` <options> [ files for install partition ]

Mandatory options:
  --device              SD block device node (e.g /dev/sdd)

Optional options:
  --version             Print version.
  --help                Print this help message.
"
  exit 1
}

check_if_main_drive ()
{
  mount | grep " on / type " > /dev/null
  if [ "$?" != "0" ]
  then
    echo "-- WARNING: not able to determine current filesystem device"
  else
    main_dev=`mount | grep " on / type " | awk '{print $1}'`
    echo "-- Main device is: $main_dev"
    echo $main_dev | grep "$device" > /dev/null
    [ "$?" = "0" ] && echo "++ ERROR: $device seems to be current main drive ++" && exit 1
  fi

}

# Check if the script was started as root or with sudo
user=`id -u`
[ "$user" != "0" ] && echo "++ Must be root/sudo ++" && exit

# Process command line...
while [ $# -gt 0 ]; do
  case $1 in
    --help | -h)
      usage $0
      ;;
    --device) shift; device=$1; shift; ;;
    --version) version $0;;
    *) copy="$copy $1"; shift; ;;
  esac
done

test -z $device && usage $0

if [ ! -b $device ]; then
   echo "ERROR: $device is not a block device file"
   exit 1;
fi

check_if_main_drive

echo "************************************************************"
echo "*         THIS WILL DELETE ALL THE DATA ON $device        *"
echo "*                                                          *"
echo "*         WARNING! Make sure your computer does not go     *"
echo "*                  in to idle mode while this script is    *"
echo "*                  running. The script will complete,      *"
echo "*                  but your SD card may be corrupted.      *"
echo "*                                                          *"
echo "*         Press <ENTER> to confirm....                     *"
echo "************************************************************"
read junk

for i in `ls -1 $device?`; do
 echo "unmounting device '$i'"
 umount $i 2>/dev/null
done

execute "dd if=/dev/zero of=$device bs=1024 count=1024"

sync

cat << END | fdisk $device
n
p
1

+62M
n
p
2


t
1
c
a
1
w
END

# handle various device names.
PARTITION1=${device}1
if [ ! -b ${PARTITION1} ]; then
        PARTITION1=${device}p1
fi

PARTITION2=${device}2
if [ ! -b ${PARTITION2} ]; then
        PARTITION2=${device}p2
fi

# make partitions.
echo "Formating ${device}1 ..."
if [ -b ${PARTITION1} ]; then
	mkfs.vfat -F 32 -n "boot" ${PARTITION1}
else
	echo "Cant find boot partition in /dev"
fi

if [ -b ${PARITION2} ]; then
	mkfs.ext4 -L "rootfs" ${PARTITION2}
else
	echo "Cant find rootfs partition in /dev"
fi

echo "Copying filesystem on ${device}1,${device}2"
execute "mkdir -p /tmp/sdk/$$/boot"
execute "mkdir -p /tmp/sdk/$$/rootfs"
execute "mount ${device}1 /tmp/sdk/$$/boot"
execute "mount ${device}2 /tmp/sdk/$$/rootfs"


execute "cp MLO /tmp/sdk/$$/boot/"
execute "cp u-boot.img /tmp/sdk/$$/boot/"
execute "cp uImage /tmp/sdk/$$/boot/"
execute "cp uImage-omap5-uevm.dtb /tmp/sdk/$$/boot/omap5-uevm.dtb"
execute "cp u-boot-omap-kvm-boot.src.sd.3-12 /tmp/sdk/$$/boot/boot.scr"

ROOTFS=kvm-image-extended-omap5-evm-kvm.tar.gz
if [ ! -f $ROOTFS ]
then
	echo "ERROR: failed to find rootfs tar in $ROOTFS"
else
	echo "Extracting filesystem on ${device}2 ..."
	execute "tar zxf $ROOTFS -C /tmp/sdk/$$/rootfs"
fi

sync
echo "unmounting ${device}1,${device}2"
execute "umount /tmp/sdk/$$/boot"
execute "umount /tmp/sdk/$$/rootfs"

execute "rm -rf /tmp/sdk/$$"
echo "completed!"

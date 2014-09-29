#!/bin/bash
#
# Geoffrey Papaux
#
# This script uses parts of:
#     - "sd_fusing.sh"
#          Copyright (C) 2013 Samsung Electronics Co., Ltd.
#                             http://www.samsung.com/
#
#     - "install-on-sdcard.sh"
#          Copyright (C) 2013, Reinhard Tartler <siretart@tauware.de>
#
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.
#

if [ -z $1 ]
then
    echo "usage: $0 <SD Reader's device file>"
    exit 0
fi

if [ -b $1 ]
then
    echo "$1 reader is identified."
    echo
    echo "This script will erase the content of $1."
    read -p "Press Enter to continue or Ctrl-C to cancel."
else
    echo "$1 is NOT identified."
    exit 0
fi

if mount | grep -q $1
then
    echo
    echo "ERROR: Partitions of device $1 are currently mounted. Please umount and try again."
    exit 0
fi

# stop on error
set -e

device="$1"

bl1_pos=1
bl2_pos=31
uboot_pos=63
tzsw_pos=719

# delete bootsector and partition table
echo
echo "Delete bootsector and partition tables..."
sudo dd if=/dev/zero of=$device bs=1024 count=4

# create new partition table
echo
echo "Create new partition table..."
sudo parted -s $device -- mklabel msdos
sudo parted -s $device -- mkpart primary fat16 4096s 266239s
sudo parted -s $device -- mkpart primary ext4 266240s 100%

# fusing the signed bootloader and u-boot
echo
echo "Writing bl1 to SD card..."
sudo dd iflag=dsync oflag=dsync if=./bl1.hardkernel.bin.signed of=${device} seek=$bl1_pos
echo
echo "Writing bl2 to SD card..."
sudo dd iflag=dsync oflag=dsync if=./bl2.u-boot-spl.bin.signed of=${device} seek=$bl2_pos
echo
echo "Writing u-boot to SD card..."
sudo dd iflag=dsync oflag=dsync if=./u-boot.bin of=${device} seek=$uboot_pos
echo
echo "Writing tzsw to SD card..."
sudo dd iflag=dsync oflag=dsync if=./tzsw.hardkernel.bin.signed of=${device} seek=$tzsw_pos


# format partitions
bootdev=${device}1
rootdev=${device}2
echo
echo "Creating BOOT and rootfs partitions..."
sudo mkfs -t vfat -n BOOT $bootdev
sudo mkfs -t ext4 -L rootfs $rootdev


# mount partitions and copy files
echo
echo "Mount BOOT and rootfs partitions on $1..."
bootmountpoint=`mktemp -d`
rootmountpoint=`mktemp -d`
sudo mount $bootdev $bootmountpoint
sudo mount $rootdev $rootmountpoint
sudo rm -rf $bootmountpoint/*
sudo rm -rf $rootmountpoint/*

echo
echo "Copy boot.ini, uImage, dtb to BOOT partition..."
sudo cp -v boot.ini $bootmountpoint/
sudo cp -v uImage $bootmountpoint/
sudo cp -v uImage-exynos5422-odroidxu3.dtb $bootmountpoint/exynos5422-odroidxu3.dtb

echo
echo "Extract root file system (kvm-image-extended-odroidxu-kvm.tar.gz) to rootfs partition..."
sudo tar xpf kvm-image-extended-odroidxu3-kvm.tar.gz -C $rootmountpoint/

# sync to sd-card
sync

# cleaning
sudo umount $bootdev
sudo umount $rootdev

echo
echo "Done. It is now safe to eject and remove your SD-Card."

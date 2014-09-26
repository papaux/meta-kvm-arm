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
    echo "usage: ./sd_fusing.sh <SD Reader's device file>"
    exit 0
fi

if [ -b $1 ]
then
    echo "$1 reader is identified."
    read -p "This will ask for sudo password and erase $1 device. Press Enter to continue or Ctrl-C to cancel"
else
    echo "$1 is NOT identified."
    exit 0
fi

device="$1"

bl1_pos=1
bl2_pos=31
uboot_pos=63
tzsw_pos=2111

# delete bootsector and partition tables
sudo dd if=/dev/zero of=$device bs=1024 count=4

# cf. http://lkcl.net/reports/odroid-u2.html
sudo parted -s $device -- mklabel msdos
sudo parted -s $device -- mkpart primary fat16 4096s 266239s
sudo parted -s $device -- mkpart primary ext4 266240s 100%

# fusing the signed bootloader and u-boot
dd iflag=dsync oflag=dsync if=./bl1.hardkernel.bin.signed of=${device} seek=$bl1_pos
dd iflag=dsync oflag=dsync if=./bl2.u-boot-spl.bin.signed of=${device} seek=$bl2_pos
dd iflag=dsync oflag=dsync if=./u-boot.bin of=${device} seek=$uboot_pos
dd iflag=dsync oflag=dsync if=./tzsw.hardkernel.bin.signed of=${device} seek=$tzsw_pos

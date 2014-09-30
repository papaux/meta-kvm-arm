require u-boot-odroid.inc

DESCRIPTION = "Patched u-boot bootloader for ODROID-XU with KVM support."

SRC_URI = "git://github.com/hardkernel/u-boot.git;protocol=https;branch=${BRANCH} \
		   file://${BL1};md5=19f1c93ee20e2db728d397903515d3a1 \
		   file://${BL2};md5=4d51f0923370232ef8734f8b9f7238a5 \
		   file://${TZSW};md5=7059f00d2fee09e7f6117b269efd5ed3 \
		   file://${BOOT_INI};md5=9197d6f8a352eca832eddc68ae3a7ada \
		   file://${SD_INST};md5=267b8e23add6932a0a069d9e24695f65 \
		  "

# This commit corresponds to odroid-v2012.07-virt latest commit at May 30 2014
SRCREV = "e71ef9c081cfffcc0372c1edc2fde0386d755a94"
BRANCH = "odroid-v2012.07"

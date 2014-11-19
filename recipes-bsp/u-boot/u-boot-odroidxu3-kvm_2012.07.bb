require u-boot-odroid.inc

DESCRIPTION = "Patched u-boot bootloader for ODROID-XU3 with KVM support"

SRC_URI = "git://github.com/hardkernel/u-boot.git;protocol=https;branch=${BRANCH} \
		   file://${BL1};md5=3908379f82f972ece88ca1b5a280b5fd \
		   file://${BL2};md5=a7702c669373ccddbb23e35f3ebeecba \
		   file://${TZSW};md5=fd01dda20b999e0b731c7063431a42b3 \
		   file://${BOOT_INI};md5=760e311d503fc4efe91f6a537f101fdf \
		   file://${SD_INST};md5=267b8e23add6932a0a069d9e24695f65 \
		  "

# This commit corresponds to odroidxu3-v2012.07 latest commit at Oct 27 2014 (Exynos5422 timer issue have been fixed)
SRCREV = "e029d5680e80c57a1e3ae32d888d166e2dec0056"
BRANCH ?= "odroidxu3-v2012.07"

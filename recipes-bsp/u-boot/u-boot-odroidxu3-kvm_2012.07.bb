require ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "Patched u-boot bootloader for ODROID-XU3 with KVM support"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "u-boot"

PKG_${PN} = "u-boot"
PKG_${PN}-dev = "u-boot-dev"
PKG_${PN}-dbg = "u-boot-dbg"

S = "${WORKDIR}/git"

PV = "2012.07"
PR = "r0+gitr${SRCPV}"

BL1 = "bl1.hardkernel.bin.signed"
BL2 = "bl2.u-boot-spl.bin.signed"
TZSW = "tzsw.hardkernel.bin.signed"
BOOT_INI = "boot.ini"
SD_INST = "install_on_sd_card.sh"

SRC_URI = "git://github.com/hardkernel/u-boot.git;protocol=https;branch=${BRANCH} \
		   file://${BL1};md5=3908379f82f972ece88ca1b5a280b5fd \
		   file://${BL2};md5=a7702c669373ccddbb23e35f3ebeecba \
		   file://${TZSW};md5=fd01dda20b999e0b731c7063431a42b3 \
		   file://${BOOT_INI};md5=ad17d0a0aca42d4c73a75e755162a9a1 \
		   file://${SD_INST};md5=267b8e23add6932a0a069d9e24695f65 \
		  "

# This commit corresponds to odroidxu3-v2012.07 latest commit at Fri Sep 19 2014 (HYP mode for the OdroidXU3 CPUs)
SRCREV = "7a2a0237a967ba1864b24d2380f5e92141d4349c"
BRANCH ?= "odroidxu3-v2012.07"

UBOOT_ENTRYPOINT = "0x40008000"
UBOOT_LOADADDRESS = "0x40008000"


do_deploy_append () {
    install -d ${DEPLOYDIR}
    cp -v ${WORKDIR}/${BL1} ${DEPLOYDIR}
    cp -v ${WORKDIR}/${BL2} ${DEPLOYDIR}
    cp -v ${WORKDIR}/${TZSW} ${DEPLOYDIR}
    cp -v ${WORKDIR}/${BOOT_INI} ${DEPLOYDIR}
    cp -v ${WORKDIR}/${SD_INST} ${DEPLOYDIR}
}

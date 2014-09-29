require ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "Patched u-boot bootloader for ODROID-XU with KVM support (credits to FiachAntaw)"

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

SRC_URI = "git://github.com/FiachAntaw/u-boot.git;protocol=https;branch=${BRANCH} \
		   file://${BL1};md5=19f1c93ee20e2db728d397903515d3a1 \
		   file://${BL2};md5=4d51f0923370232ef8734f8b9f7238a5 \
		   file://${TZSW};md5=7059f00d2fee09e7f6117b269efd5ed3 \
		   file://${BOOT_INI};md5=9197d6f8a352eca832eddc68ae3a7ada \
		   file://${SD_INST};md5=267b8e23add6932a0a069d9e24695f65 \
		  "

# This commit corresponds to odroid-v2012.07-virt latest commit at May 30 2014
SRCREV = "e71ef9c081cfffcc0372c1edc2fde0386d755a94"
BRANCH ?= "odroid-v2012.07-virt"

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

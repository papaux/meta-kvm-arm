require ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "Patched u-boot bootloader for odroid xu with kvm support (by fanta)"

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

SRC_URI = "git://github.com/FiachAntaw/u-boot.git;protocol=https;branch=${BRANCH} \
		   file://u-boot-odroidxu-kvm-spl.bin.signed;md5=4d51f0923370232ef8734f8b9f7238a5 \
		  "

UBOOT_ENTRYPOINT = "0x40008000"
UBOOT_LOADADDRESS = "0x40008000"


BRANCH ?= "odroid-v2012.07-virt"

# This commit corresponds to odroid-v2012.07-virt latest commit at May 30 2014
SRCREV = "e71ef9c081cfffcc0372c1edc2fde0386d755a94"

#SPL_BINARY = "MLO"
SPL_UART_BINARY = "u-boot-odroidxu-kvm-spl.bin.signed"



# Taken from u-boot-ti.inc
# SPL (Second Program Loader) to be loaded over UART
SPL_UART_BINARY ?= ""
SPL_UART_IMAGE ?= "${SPL_UART_BINARY}-${MACHINE}-${PV}-${PR}"
SPL_UART_SYMLINK ?= "${SPL_UART_BINARY}-${MACHINE}"

do_install_append () {
    if [ "x${SPL_UART_BINARY}" != "x" ]
    then
        install ${WORKDIR}/${SPL_UART_BINARY} ${D}/boot/${SPL_UART_IMAGE}
        ln -sf ${SPL_UART_IMAGE} ${D}/boot/${SPL_UART_BINARY}
    fi
}

do_deploy_append () {
    cd ${DEPLOYDIR}
    if [ "x${SPL_UART_BINARY}" != "x" ]
    then
        install ${WORKDIR}/${SPL_UART_BINARY} ${DEPLOYDIR}/${SPL_UART_IMAGE}
        rm -f ${DEPLOYDIR}/${SPL_UART_BINARY} ${DEPLOYDIR}/${SPL_UART_SYMLINK}
        ln -sf ${SPL_UART_IMAGE} ${DEPLOYDIR}/${SPL_UART_BINARY}
        ln -sf ${SPL_UART_IMAGE} ${DEPLOYDIR}/${SPL_UART_SYMLINK}
    fi
}


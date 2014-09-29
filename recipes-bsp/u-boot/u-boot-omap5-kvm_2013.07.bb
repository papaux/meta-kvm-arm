require ${COREBASE}/meta-ti/recipes-bsp/u-boot/u-boot-ti.inc

DESCRIPTION = "Custom u-boot bootloader for omap5 with kvm support"


PV = "2013.07"
PR = "r0+gitr${SRCPV}"

SRC_URI = "git://github.com/papaux/u-boot-omap5.git;protocol=https;branch=${BRANCH} \
          file://u-boot-omap-kvm-boot.src.sd.3-12;md5=036a2e04c8d52adfe06259e79f5e45bf"

BRANCH ?= "v2013.07-omap5-usbnet-hyp"

# This commit corresponds to ti2013.04.00 release tag
SRCREV = "8bf2f9750ffb860540dd11d3f2cf6cc07b52654d"

SPL_BINARY = "MLO"
SPL_UART_BINARY = "u-boot-spl.bin"

# Copy the boot script
SPL_BOOTSCR_BINARY ?= "u-boot-omap-kvm-boot.src.sd.3-12"
SPL_BOOTSCR_IMAGE ?= "${SPL_BOOTSCR_BINARY}-${MACHINE}-${PV}-${PR}"
SPL_BOOTSCR_SYMLINK ?= "${SPL_BOOTSCR_BINARY}-${MACHINE}"


do_install_append () {
    if [ "x${SPL_BOOTSCR_BINARY}" != "x" ]
    then
        install ${WORKDIR}/${SPL_BOOTSCR_BINARY} ${D}/boot/${SPL_BOOTSCR_IMAGE}
        ln -sf ${SPL_BOOTSCR_IMAGE} ${D}/boot/${SPL_BOOTSCR_BINARY}
    fi
}

do_deploy_append () {
    cd ${DEPLOYDIR}
    if [ "x${SPL_BOOTSCR_BINARY}" != "x" ]
    then
        install ${WORKDIR}/${SPL_BOOTSCR_BINARY} ${DEPLOYDIR}/${SPL_BOOTSCR_IMAGE}
        rm -f ${DEPLOYDIR}/${SPL_BOOTSCR_BINARY} ${DEPLOYDIR}/${SPL_BOOTSCR_SYMLINK}
        ln -sf ${SPL_BOOTSCR_IMAGE} ${DEPLOYDIR}/${SPL_BOOTSCR_BINARY}
        ln -sf ${SPL_BOOTSCR_IMAGE} ${DEPLOYDIR}/${SPL_BOOTSCR_SYMLINK}
    fi
}


require ${COREBASE}/meta-ti/recipes-bsp/u-boot/u-boot-ti.inc

DESCRIPTION = "Custom u-boot bootloader for omap5 with kvm support"


PV = "2013.07"
PR = "r0+gitr${SRCPV}"

BRANCH ?= "v2013.07-omap5-usbnet-hyp"

# This commit corresponds to ti2013.04.00 release tag
SRCREV = "8bf2f9750ffb860540dd11d3f2cf6cc07b52654d"

SPL_BINARY = "MLO"
SPL_UART_BINARY = "u-boot-spl.bin"

# Copy the boot script
SPL_BOOTSCR ?= "u-boot-omap-kvm-boot.src.sd.3-12"

SD_INST = "install_on_sd_card.sh"

SRC_URI = "git://github.com/papaux/u-boot-omap5.git;protocol=https;branch=${BRANCH} \
          file://u-boot-omap-kvm-boot.src.sd.3-12;md5=036a2e04c8d52adfe06259e79f5e45bf \
          file://${SD_INST};md5=4667c783d6c148db7a0618b0e8e01c4b"


do_deploy_append () {
    install -d ${DEPLOYDIR}
    cp -v ${WORKDIR}/${SPL_BOOTSCR} ${DEPLOYDIR}
    cp -v ${WORKDIR}/${SD_INST} ${DEPLOYDIR}
}


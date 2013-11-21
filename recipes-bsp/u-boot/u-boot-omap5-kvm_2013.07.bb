require ${COREBASE}/meta-ti-glsdk/recipes-bsp/u-boot/u-boot-ti.inc

DESCRIPTION = "Custom u-boot bootloader for omap5 with kvm support"

PR = "r0+gitr${SRCPV}"

SRC_URI = "git://forge.tic.eia-fr.ch/git/geoffrey.papaux/u-boot-omap5.git;protocol=https;branch=${BRANCH}"

BRANCH ?= "papaux/v2013.07-omap5-usbnet-hyp"

# This commit corresponds to ti2013.04.00 release tag
SRCREV = "8bf2f9750ffb860540dd11d3f2cf6cc07b52654d"

SPL_BINARY = "MLO"
SPL_UART_BINARY = "u-boot-spl.bin"

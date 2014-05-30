require ${COREBASE}/meta-ti/recipes-bsp/u-boot/u-boot-ti.inc

DESCRIPTION = "Patched u-boot bootloader for odroid xu with kvm support (by fanta)"


PV = "2012.07"
PR = "r0+gitr${SRCPV}"

SRC_URI = "git://github.com/FiachAntaw/u-boot.git;protocol=https;branch=${BRANCH} \
		  "

BRANCH ?= "odroid-v2012.07-virt"

# This commit corresponds to odroid-v2012.07-virt latest commit at May 30 2014
SRCREV = "e71ef9c081cfffcc0372c1edc2fde0386d755a94"

SPL_BINARY = "MLO"
SPL_UART_BINARY = "u-boot-spl.bin"

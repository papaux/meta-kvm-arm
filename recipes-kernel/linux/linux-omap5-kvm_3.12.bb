SECTION = "kernel"
DESCRIPTION = "Custom Linux kernel for omap5 device and kvm"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
KERNEL_IMAGETYPE = "uImage"

inherit kernel

require recipes-kernel/linux/linux-dtb.inc
require recipes-kernel/linux/setup-defconfig.inc

KERNEL_DEVICETREE = "arch/arm/boot/dts/omap5-uevm.dts"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

KERNEL_FEATURES_append = " features/kvm/qemu-kvm-enable.scc"

COMPATIBLE_MACHINE = "omap-a15"

S = "${WORKDIR}/git"

BRANCH = "papaux/3.12-omap5-kvm"

# This commit corresponds to ti2013.04.02 release tag
SRCREV = "4adcfa4b809bc8efa04884aa2518b2e7c9900e67"
PV = "3.12"

# Append to the MACHINE_KERNEL_PR so that a new SRCREV will cause a rebuild
MACHINE_KERNEL_PR_append = "f+gitr${SRCPV}"

SRC_URI = "git://forge.tic.eia-fr.ch/geoffrey.papaux/linux-omap5.git;protocol=ssh;branch=${BRANCH};user=git \
           file://defconfig \
          "

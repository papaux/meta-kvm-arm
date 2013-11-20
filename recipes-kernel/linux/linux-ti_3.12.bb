SECTION = "kernel"                                                                                                                                                               [0/1977]
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

BRANCH = "ti-linux-3.12.y"

# A commit ID working at this time, we could try with newer commit ids... 
SRCREV = "abf62fd7ffe1ea06669792c968d9d11e30dcd326"
PV = "3.12.0"

# Append to the MACHINE_KERNEL_PR so that a new SRCREV will cause a rebuild
MACHINE_KERNEL_PR_append = "f+gitr${SRCPV}"

SRC_URI = "git://git.ti.com/ti-linux-kernel/ti-linux-kernel.git;protocol=git;branch=${BRANCH}  \
	   file://0001-omap5-kvm-patch-dts-for-vgic-support-in-kvm.patch \
           file://0002-omap5-kvm-patch-for-lpae-support-in-platform-drivers.patch \
           file://defconfig \
          "


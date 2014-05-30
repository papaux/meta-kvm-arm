FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_omap5-evm-kvm = "file://0001-omap5-kvm-patch-dts-for-vgic-support-in-kvm.patch \
				file://0002-omap5-kvm-patch-for-lpae-support-in-platform-drivers.patch \
				file://0003-KVM-ARM-add-hardware-PMU-in-omap5-device-tree.patch \
				"


require recipes-kernel/linux/linux-hardkernel.inc

COMPATIBLE_MACHINE = "odroidxu"

# Specify dtb file
KERNEL_DEVICETREE_odroidxu-kvm = "exynos5410-odroidxu.dtb"

BRANCH = "odroid-3.13.y-linaro"
SRCREV = "54840781e2b5ee90632b62b232463020a913f439"
PV = "3.13.11"

SRC_URI_append_odroidxu-kvm = "file://0001-Exynos5410-PWM0-now-working.patch \
				"


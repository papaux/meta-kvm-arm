require recipes-kernel/linux/linux-hardkernel.inc

COMPATIBLE_MACHINE = "odroidxu3"

# Specify dtb file
KERNEL_DEVICETREE_odroidxu3-kvm = "exynos5422-odroidxu3.dtb"

BRANCH = "odroidxu3-3.10.y"

# Commit from Wed Sep 24 2014
SRCREV = "2f4ae31040d6fca3fae303f6b65d8e219d416133"
PV = "3.10.54"

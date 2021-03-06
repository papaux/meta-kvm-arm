SOC_FAMILY = "odroid-a15"
require conf/machine/include/soc-family.inc

require conf/machine/include/tune-cortexa15.inc

# Increase this everytime you change something in the kernel
MACHINE_KERNEL_PR = "r0"

KERNEL_IMAGETYPE = "uImage"

UBOOT_ARCH = "arm"
UBOOT_ENTRYPOINT = "0x40008000"
UBOOT_LOADADDRESS = "0x40008000"

EXTRA_IMAGEDEPENDS += "virtual/bootloader"

GUI_MACHINE_CLASS = "smallscreen"

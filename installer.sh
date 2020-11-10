mkdir -p /usr/share/java/tuf-controller/
cp target/tuf-controller.jar /usr/share/java/tuf-controller/
cp src/main/resources/images/tuf_logo.png /usr/share/icons/tuf-controller.png
echo '[Desktop Entry]
Type=Application
Name=TUF Controller
Comment=A simple GUI to change keyboard led color and fan modes in asus TUF laptop
Exec=tuf-controller
Icon=tuf-controller
Terminal=false
Categories=Settings;System;' > /usr/share/applications/tuf-controller.desktop
echo '#!/bin/bash
/usr/bin/java -jar /usr/share/java/tuf-controller/tuf-controller.jar  "$@"' > /usr/bin/tuf-controller
chmod +x /usr/bin/tuf-controller

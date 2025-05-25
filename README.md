# Smart lighting - app
## Project Overview

The smart lights project consists of the two repositories: [Arduino code](https://github.com/MaybeAshLately/smartLights) and [Android app](https://github.com/MaybeAshLately/lightControl). The aim of the project is to create simple prototype of smart lightning, based on Arduino and app to control it.

## App part overview

This repository contains Android app. The code is responsible for sending commands (through bluetooth) to control the smart lights and turning it on in one of four modes:
- NORMAL - the RGB LED turns on in received color and brightness,
- PHOTORESISTOR - the RGB LED turns on in received color and brightness when the photoresistor reports light level under received level,
- MOTION - the RGB LED turns on in received color and brightness after PIR sensor detects the movement,
- TV - the RGB LED is blinking in random colors (to simulate the TV in a room).

## License

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see https://www.gnu.org/licenses/.
# DAPNET CORE #
## Introduction ##
The DAPNET CORE offers the main functionality of the Decentralized Amateur Paging Network.
It builds up a cluster together with other DAPNET Core instances over the HAMNET and
controls connected paging transmitter. All functions can be accessed via the REST API.

## Installation and Requirements ##
Using the packed release version no installation is required. All external libraries are
included. Just make sure a JAVA 8+ runtime environment is available.

Detailed installation instructions are avaiable here https://www.afu.rwth-aachen.de/projekte/funkruf-pager-pocsag/funkrufmaster-2-0-dapnet/anleitung-zum-installieren-neuer-knoten (for now just in German)

## Usage ##
### Basic Configuration ###
#### Cluster Configuration ####
If you would like to join an existing cluster contact an admin. He will register your
DAPNET Core instance and inform you about the configuration. If you want to create a new
cluster feel free to set your own configuration. In each case set the following parameters
in the `local/config/ClusterConfig.xml`:

* Node name (name of your DAPNET Core instance) at
  `<pbcast.GMS name="NodeName@ClusterName"/>`
* Cluster name (name of the DAPNET Cluster) at `<pbcast.GMS name="NodeName@ClusterName"/>`
* Node authentication key (needed for authentication if you want to rejoin your cluster
  later) of form XXXX-XXXX-XXXX-XXXX-XXXX (X is alphanumeric) at
  `<AUTH auth_value="key"/>`
* Initial hosts (if you want to join an existing cluster at least one node have to be
  known... ) at `<TCPPING initial_hosts="HostA[7800],HostB[7800]\>`

#### Log Settings ####
The DAPNET Core uses Log4j2 for logging, which can be configured in the
`config/LogSettings.xml`. The default setting will inform you about nearly all events in
the console and additionally will store all logs in a file.

#### General Settings ####
In the `Setting.json` file various parameters can be set. The default settings should fit
for most use cases. However, important could be the `"port": 8080"` line to configure the
port of the REST API.

### First Start ###
1. Make sure you performed the basic configuration as described above.
2. Start the application with `java -Dlog4j.configurationFile=../local/config/LogSettings_REST.xml -jar dapnet-core-x.x.x.jar`
3. In case you connect to an existing cluster all current data will be automatically
   downloaded. If you create a new cluster and no `date/state.json` is found the
   parameters of your node will be set to default values. Additionally, a default user
   `admin` with password `admin` will be automatically created, which you can used to
   connect with the REST interface.
4. After a successful startup you can start to play with the REST API
   ([API Description](https://bitbucket.org/DAPNET/dapnet-core/wiki/Beschreibung%20der%20REST%20API))
5. In case you created a new cluster please set all parameters of your node (e.g.
   longitude and latitude), set the `admin` user's mail address and especially set a new
   password.

## Used Software ##
TODO

## Contribution ##
TODO

## More Information ##
TODO

**DAPNET CORE PROJECT | Copyright (C) 2017**

**Institut für Hochfrequenztechnik | RWTH AACHEN UNIVERSITY**

Melatener Str. 25 | 52074 Aachen

Daniel Sialkowski | daniel.sialkowski@rwth-aachen.de

Ralf Wilke | wilke@ihf.rwth-aachen.de

Philipp Thiel

## License
The code of this project is licensed under the **GNU AGPLv3+**.
>Copyright (C) 2018 AFU RWTH Aachen

>This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.

<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/80x15.png" /></a><br />
Images, assets, data, documentation and everything else is licensed under a [Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License](https://creativecommons.org/licenses/by-nc-sa/4.0/) aka **CC BY-NC-SA 4.0**.
For further information take a look at the files **LICENSE-code** and **LICENSE-assets**.
If you want to use our work under a different license, e.g. for further commercial use, please contact us at  rwth-afu [at] online.de or take a look at [AFU RWTH](https://www.afu.rwth-aachen.de/ueber-uns/kontakt).
Used libraries, projects and artworks and their respective licenses are available in the **LICENSES.md** file.

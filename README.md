# Nordic to QuakeML converter tool package

## How to install

## Modules
Components | Description
---------- | -----------
converter-core | Reading and mapping formats 
converter-standalone | Executable jar file for conversion
converter-web-client | Web interface (front end) for the converter
converter-web-server | Web server (back end) for the converter
quakeml-web-service | Service for extracting data from a QuakeML based database. Both QuakeML and Nordic format is supported as output
quakeml-web-service-ingestor | Executable jar file for ingesting the Seisan catalog into database
seisan-quakeml-commons-file | Shared tools for file handling
seisan-quakeml-commons-jpa | The Nordic format and QuakeML represented as java objects
seisan-quakeml-commins-web | Shared tools for interactions with web

## Mapping
Mapping between QuakeML entities and Nordic lines. Check the excel file under documentation folder for more detailed information.

- Amplitude (From Nordic line type 1 and 4)
- Arrival (From Nordic line type 1 and 4)
- Event (From Nordic line type 1, 3, 6, I and S)
- FocalMechanism (From Nordic line type F)
- Magnitude (From Nordic line type 1)
- MomentTensor (From Nordic line type M)
- Origin (From Nordic line type 1 and E)
- OriginUncertainty (Currently no mapping)
- Pick (From Nordic line type 1 and 4)
- StationMagnitude (Currently no mapping)
- StationMagnitudeContribution (Currently no mapping)

import { MappingAttributes } from 'src/app/shared/models/mappingattributes.model';

export const EventAttributes: MappingAttributes[] = [
    {
        qmlAttribute: 'publicID',
        qmlType: 'co:ResourceReference [1..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: ['1'],
        seisanFields: ['Year, Month, Day'],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: ['Generated ID']
    },
    {
        qmlAttribute: 'comment',
        qmlType: 'rm:Comment [0..*]',
        qmlNested: ['text', 'id', 'CreationInfo'],
        qmlFormat: ['String', 'String', 'Object'],
        qmlUnit: [],
        seisanLines: ['3, 6, I'],
        seisanFields: [],
        seisanColumns: ['1-80'],
        seisanFormat: ['String'],
        seisanUnit: [],
        comments: ['All line 3s, 6s, Is', 'Generated to identify line type']
    },
    {
        qmlAttribute: 'description',
        qmlType: 'bedt:EventDescription [0..*]',
        qmlNested: ['text', 'enum (EventDescriptionType)'],
        qmlFormat: ['String', 'String'],
        qmlUnit: [],
        seisanLines: ['3', '3'],
        seisanFields: ['text', 'text'],
        seisanColumns: ['2-79', '2-79'],
        seisanFormat: ['String', 'String'],
        seisanUnit: [],
        comments: ['Text after "LOCALITY:" marker', 'Set to enum type region name if marker exists']
    },
    {
        qmlAttribute: 'preferredOriginID',
        qmlType: 'co:ResourceReference [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: ['1'],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: ['First line 1. Pointer to the related Origin entity']
    },
    {
        qmlAttribute: 'preferredMagnitudeID',
        qmlType: 'co:ResourceReference [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: ['Not determined. Pointer to the related Magnitude entity']
    },
    {
        qmlAttribute: 'preferredFocalMechanismID',
        qmlType: 'co:ResourceReference [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: ['First listed. Pointer to the related FocalMechanism entity']
    },
    {
        qmlAttribute: 'type',
        qmlType: 'bedt:EventType [0..1]',
        qmlNested: [],
        qmlFormat: ['String (enum)'],
        qmlUnit: [],
        seisanLines: ['1'],
        seisanFields: ['Event ID'],
        seisanColumns: ['23'],
        seisanFormat: ['String'],
        seisanUnit: [],
        comments: [
                'Q = "earthquake"',
                'E = "explosion"',
                'P = "explosion"',
                'I = " induced or triggered event"',
                'O = "other event"',
                'S = "sonic boom"',
                'C = "ice quake", use G back to nordic',
                'G = "ice quake"',
                'L = "landslide"',
                'X = "landslide"',
                'V = "volcanic eruption"',
                'black = "not reported"'
            ]
    },
    {
        qmlAttribute: 'typeCertainty',
        qmlType: 'bedt:EventTypeCertainty [0..1]',
        qmlNested: [],
        qmlFormat: ['String (enum)'],
        qmlUnit: [],
        seisanLines: ['1'],
        seisanFields: ['Event ID'],
        seisanColumns: ['23'],
        seisanFormat: ['String'],
        seisanUnit: [],
        comments: [
            'E = "known"',
            'Q = "known"',
            'P = "suspected"',
            'black = "suspected"'
        ]
    },
    {
        qmlAttribute: 'creationInfo',
        qmlType: 'rm:CreationInfo [0..1]',
        qmlNested: ['author', 'authorURI', 'agencyID', 'creationTime', 'version', 'copyrightOwner', 'copyrightOwnerURI', 'license'],
        qmlFormat: ['String', 'String', 'String', 'String', 'String', 'String', 'String', 'String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    }
  ];

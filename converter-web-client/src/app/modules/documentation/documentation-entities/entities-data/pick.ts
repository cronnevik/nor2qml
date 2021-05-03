import { MappingAttributes } from 'src/app/shared/models/mappingattributes.model';

export const PickAttributes: MappingAttributes[] = [
    {
        qmlAttribute: 'publicID',
        qmlType: 'co:ResourceReference [1..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: ['1'],
        seisanFields: ['Year'],
        seisanColumns: ['2-5'],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'time',
        qmlType: 'co:TimeQuantity',
        qmlNested: [
            'value', 'uncertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [
            'datetime', 'float', 'float', 'float', 'float'
        ],
        qmlUnit: [
            'UTC, ISO 8601 String', 's', 's', 's', '%'
        ],
        seisanLines: [
            '1',
            '4'
        ],
        seisanFields: [
            'year, month, day',
            'hour, minutes, seconds'
        ],
        seisanColumns: [
            '2-5, 7-8, 9-10',
            '19-20, 21-22, 23-28'
        ],
        seisanFormat: [
            'int, int, int',
            'int, int, float'
        ],
        seisanUnit: [],
        comments: [
            'The nordic format extends the hours above midnght instead if a new day'
        ]
    },
    {
        qmlAttribute: 'waveformID',
        qmlType: 'wf:WaveformStreamID [1..1]',
        qmlNested: ['networkCode', 'stationCode', 'channelCode', 'locationCode', 'resourceURI'],
        qmlFormat: ['string', 'string', 'string', 'string', 'string'],
        qmlUnit: [],
        seisanLines: [
            '-',
            '4',
            '4',
            '-',
            '-'
        ],
        seisanFields: [
            '-',
            'Station Name',
            'Instrument Type & Component',
            '-',
            '-'
        ],
        seisanColumns: [
            '-',
            '2-6',
            '7 & 8',
            '-',
            '-'
        ],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            '-',
            '-',
            'Channelcode in QuakeML is 3 char, Nordic can only produce 2. Insert a "?" in the middle. First 2 char back to Nordic',
            '-',
            '-'
        ]
    },
    {
        qmlAttribute: 'comment',
        qmlType: 'rm:Comment [0..*]',
        qmlNested: [
            'text', 'id', 'creationInfo'
        ],
        qmlFormat: ['String', 'String', 'CreationInfo'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'methodID',
        qmlType: 'co:ResourceReference [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'filterID',
        qmlType: 'co:ResourceReference [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'backazimuth',
        qmlType: 'co:RealQuantity',
        qmlNested: [
            'value', 'uncertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [
            'float', 'float', 'float', 'float', 'float'
        ],
        qmlUnit: [
            'deg', 'deg', 'deg', 'deg', '%'
        ],
        seisanLines: ['4'],
        seisanFields: ['Direction of Approach, back azimuth'],
        seisanColumns: ['47-51'],
        seisanFormat: ['float'],
        seisanUnit: ['deg'],
        comments: []
    },
    {
        qmlAttribute: 'horizontalSlowness',
        qmlType: 'co:RealQuantity',
        qmlNested: [
            'value', 'uncertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [
            'float', 'float', 'float', 'float', 'float'
        ],
        qmlUnit: [
            's · deg−1', 's · deg−1', 's · deg−1', 's · deg−1', '%'
        ],
        seisanLines: ['4'],
        seisanFields: ['Phase Velocity'],
        seisanColumns: ['53-56'],
        seisanFormat: ['float'],
        seisanUnit: ['Km/second'],
        comments: ['Conversion from Km/sec to s · deg−1']
    },
    {
        qmlAttribute: 'slownessMethodID',
        qmlType: 'co:ResourceReference [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'onset',
        qmlType: 'bedt:PickOnset [0..1]',
        qmlNested: [],
        qmlFormat: ['String (Enum)'],
        qmlUnit: [],
        seisanLines: ['4'],
        seisanFields: ['Quality Indicator'],
        seisanColumns: ['10'],
        seisanFormat: ['String'],
        seisanUnit: [],
        comments: [
            'Nordic => QuakeML',
            'I => Impulsive',
            'E => Emergent',
            'default: Questionable'
        ]
    },
    {
        qmlAttribute: 'phaseHint',
        qmlType: 'bedt:Phase [0..1]',
        qmlNested: ['code'],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: ['4'],
        seisanFields: ['Phase ID'],
        seisanColumns: ['11-14 or 11-18'],
        seisanFormat: ['String'],
        seisanUnit: [],
        comments: [
            'The nested code attribute is normally neglected in the QuakeML output',
            'The string itself then goes directly into the phase attribute'
        ]
    },
    {
        qmlAttribute: 'polarity',
        qmlType: 'bedt:PickPolarity [0..1]',
        qmlNested: [],
        qmlFormat: ['String (Enum)'],
        qmlUnit: [],
        seisanLines: ['4'],
        seisanFields: ['First Motion'],
        seisanColumns: ['17'],
        seisanFormat: ['String'],
        seisanUnit: [],
        comments: [
            'Nordic => QuakeML',
            'C => Positive',
            'D => Negative',
            'default: Undecidable'
        ]
    },
    {
        qmlAttribute: 'evaluationMode',
        qmlType: 'co:EvaluationMode [0..1]',
        qmlNested: [],
        qmlFormat: ['String (enum)'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'evaluationStatus',
        qmlType: 'co:EvaluationStatus [0..1]',
        qmlNested: [],
        qmlFormat: ['String (enum)'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
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

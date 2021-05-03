import { MappingAttributes } from 'src/app/shared/models/mappingattributes.model';

export const AmplitudeAttributes: MappingAttributes[] = [
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
        qmlAttribute: 'genericAmplitude',
        qmlType: 'co:RealQuantity [1..1]',
        qmlNested: [
            'value', 'uncertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [
            'float', 'float', 'float', 'float', 'float'
        ],
        qmlUnit: [
            'See Unit attribute below',
            'See Unit attribute below',
            'See Unit attribute below',
            'See Unit attribute below',
            '%'
        ],
        seisanLines: ['4'],
        seisanFields: ['Amplitude (Zero-Peak), Duration'],
        seisanColumns: ['34-40, 30-33'],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'This Attribute can be displacement, velocity or a period',
            'Nordic:',
            'Normally units of nm, nm/s, nm/s^2 or counts',
            'Unit is identified by the Phase ID:',
            'AML, AMB, AMS, IAML,lamb, IAMs_20 ==> nm',
            'IVmB_BB, IVMs_BB ==> nm/s',
            'If Duration(CODA) exists in the Nordic format, set genericAmplitude to be Duration value',
            'QuakeML: ',
            'see the bedt:AmplitudeUnit attribute below',
            'Nordic => quakeML:',
            'convert nm => m',
            'convert nm/s => m/s',
            'An assumption for unit conversion is that the amplitude is regarded as ground motion'
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
        qmlAttribute: 'type',
        qmlType: 'string [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: ['4'],
        seisanFields: ['Phase ID'],
        seisanColumns: ['11-14 or 11-18'],
        seisanFormat: ['String'],
        seisanUnit: [],
        comments: [
            'If Duration(CODA) exists set type to END'
        ]
    },
    {
        qmlAttribute: 'unit',
        qmlType: 'bedt:AmplitudeUnit [0..1]',
        qmlNested: [],
        qmlFormat: ['String (Enum)'],
        qmlUnit: [],
        seisanLines: ['4'],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'Possible units:',
            'm, s, m/s, m/(s*s), m*s, dimensionless, other',
            'If Duration(CODA) exists in Nordic file => set unit to s'
        ]
    },
    {
        qmlAttribute: 'category',
        qmlType: 'bedt:AmplitudeCategory [0..1]',
        qmlNested: [],
        qmlFormat: ['String (Enum)'],
        qmlUnit: [],
        seisanLines: ['4'],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'Depends on the physical quantity of genericAmplitude attribute',
            'If Duration(CODA) exists set category to duration'
        ]
    },
    {
        qmlAttribute: 'period',
        qmlType: 'co:RealQuantity [0..1]',
        qmlNested: [
            'value', 'uncertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [
            'float', 'float', 'float', 'float', 'float'
        ],
        qmlUnit: [
            's', 's', 's', 's', '%'
        ],
        seisanLines: ['4'],
        seisanFields: ['Period'],
        seisanColumns: ['42-45'],
        seisanFormat: ['float'],
        seisanUnit: ['s'],
        comments: []
    },
    {
        qmlAttribute: 'timeWindow',
        qmlType: 'co:TimeWindow [0..1]',
        qmlNested: [
            'begin', 'end', 'reference'
        ],
        qmlFormat: [
            'float', 'float', 'datetime'
        ],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
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
        qmlAttribute: 'pickID',
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
        qmlAttribute: 'magnitudeHint',
        qmlType: 'string [0..1]',
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
        qmlAttribute: 'scalingTime',
        qmlType: 'co:TimeQuantity [0..1]',
        qmlNested: ['value', 'uncertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'],
        qmlFormat: ['datetime', 'float', 'float', 'float', 'float'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'snr',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: ['float'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
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

import { MappingAttributes } from 'src/app/shared/models/mappingattributes.model';

export const MagnitudeAttributes: MappingAttributes[] = [
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
        qmlAttribute: 'mag',
        qmlType: 'co:RealQuantity [1..1]',
        qmlNested: ['value', 'uncertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'],
        qmlFormat: ['float', 'float', 'float', 'float', 'float'],
        qmlUnit: ['-', '-', '-', '-', '%'],
        seisanLines: ['1'],
        seisanFields: ['Magnitude No. 1, 2 or 3'],
        seisanColumns: ['56-59, 64-67 or 72,75'],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
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
        seisanLines: ['1'],
        seisanFields: ['Type of Magnitude'],
        seisanColumns: ['60, 68 or 76'],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'Seisan uses only one character, while QuakeML have two:',
            'L = ML, b = mb, B = mB, s = Ms',
            'S = MS, W = MW, G = MbLg, C = Mc'
        ]
    },
    {
        qmlAttribute: 'originID',
        qmlType: 'co:ResourceReference [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'Pointer to related Origin entity',
        ]
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
        qmlAttribute: 'stationCount',
        qmlType: 'int [0..1]',
        qmlNested: [],
        qmlFormat: ['int'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'azimuthalGap',
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
        seisanLines: [
            '-', '-', '1'
        ],
        seisanFields: [
            '-', '-', 'Magnitude Reporting Agency 1,2 or 3'
        ],
        seisanColumns: [
            '-', '-', '61-63, 69-71, 77-79'
        ],
        seisanFormat: [
            '-', '-', 'String'
        ],
        seisanUnit: [],
        comments: [
            '-', '-', 'Back to seisan: take first 3 char if the String is longer than 3 chars'
        ]
      }
];

import { MappingAttributes } from 'src/app/shared/models/mappingattributes.model';

export const MomentTensorAttributes: MappingAttributes[] = [
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
        qmlAttribute: 'derivedOriginID',
        qmlType: 'co:ResourceReference [1..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: ['Pointer to the derived Origin entity']
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
        qmlAttribute: 'dataUsed',
        qmlType: 'bedt:DataUsed [0..*]',
        qmlNested: [
            'componentCount',
            'longestPeriod',
            'shortestPeriod',
            'stationCount',
            'waveType (bedt:DataUsedWaveType)'
        ],
        qmlFormat: [
            'int [0..1]',
            'float [0..1]',
            'float [0..1]',
            'int [0..1]',
            'String (enum)'
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
        qmlAttribute: 'momentMagnitudeID',
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
        qmlAttribute: 'tensor',
        qmlType: 'bedt:Tensor [0..1]',
        qmlNested: [
            'Mrr (co:RealQuantity) => value',
            'Mrr (co:RealQuantity) => uncertainty',
            '-',
            'Mtt (co:RealQuantity) => value',
            'Mtt (co:RealQuantity) => uncertainty',
            '-',
            'Mpp (co:RealQuantity) => value',
            'Mpp (co:RealQuantity) => uncertainty',
            '-',
            'Mrt (co:RealQuantity) => value',
            'Mrt (co:RealQuantity) => uncertainty',
            '-',
            'Mrp (co:RealQuantity) => value',
            'Mrp (co:RealQuantity) => uncertanty',
            '-',
            'Mtp (co:RealQuantity) => value',
            'Mtp (co:RealQuantity) => uncertainty',
        ],
        qmlFormat: [
            'float', 'float', '-',
            'float', 'float', '-',
            'float', 'float', '-',
            'float', 'float', '-',
            'float', 'float', '-',
            'float', 'float'
        ],
        qmlUnit: [
            'Nm', 'Nm', '-',
            'Nm', 'Nm', '-',
            'Nm', 'Nm', '-',
            'Nm', 'Nm', '-',
            'Nm', 'Nm', '-',
            'Nm', 'Nm'
        ],
        seisanLines: [
            'M2', '-', '-',
            'M2', '-', '-',
            'M2', '-', '-',
            'M2', '-', '-',
            'M2', '-', '-',
            'M2', '-'
        ],
        seisanFields: [
            'Mrr or Mzz', '-', '-',
            'Mtt or Mxx', '-', '-',
            'Mpp or Myy', '-', '-',
            'Mrt or Mzx', '-', '-',
            'Mrp or Mzy', '-', '-',
            'Mtp or Mxy', '-'
        ],
        seisanColumns: [
            '4-9', '-', '-',
            '11-16', '-', '-',
            '18-23', '-', '-',
            '25-30', '-', '-',
            '32-37', '-', '-',
            '39-44', '-'
        ],
        seisanFormat: [
            'float', '-', '-',
            'float', '-', '-',
            'float', '-', '-',
            'float', '-', '-',
            'float', '-', '-',
            'float', '-'
        ],
        seisanUnit: [
            'Nm', '-', '-',
            'Nm', '-', '-',
            'Nm', '-', '-',
            'Nm', '-', '-',
            'Nm', '-', '-',
            'Nm'
        ],
        comments: []
    },
    {
        qmlAttribute: 'scalarMoment',
        qmlType: 'co:RealQuantity [0..1]',
        qmlNested: [
            'value', 'undertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [
            'float', 'float', 'float', 'float', 'float',
        ],
        qmlUnit: [
            'Nm', 'Nm', 'Nm', 'Nm', '%'
        ],
        seisanLines: [
            'M2'
        ],
        seisanFields: [
            'Scalar Moment'
        ],
        seisanColumns: [
            '53-62'
        ],
        seisanFormat: [
            'Nm'
        ],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'variance',
        qmlType: 'co:RealQuantity [0..1]',
        qmlNested: [
            'value', 'undertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'Represented as pure float instead',
            'of RealQuantity in QuakeML v1.2'
        ]
    },
    {
        qmlAttribute: 'varianceReduction',
        qmlType: 'co:RealQuantity [0..1]',
        qmlNested: [
            'value', 'undertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'Represented as pure float instead',
            'of RealQuantity in QuakeML v1.2'
        ]
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
        qmlAttribute: 'greensFunctionID',
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
        seisanLines: ['M2'],
        seisanFields: ['Method Used'],
        seisanColumns: ['71-77'],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'category',
        qmlType: 'bedt:MomentTensorCategory [0..1]',
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
        qmlAttribute: 'clvd',
        qmlType: 'co:RealQuantity [0..1]',
        qmlNested: [
            'value', 'undertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'Represented as pure float instead',
            'of RealQuantity in QuakeML v1.2'
        ]
    },
    {
        qmlAttribute: 'doubleCouple',
        qmlType: 'co:RealQuantity [0..1]',
        qmlNested: [
            'value', 'undertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'Represented as pure float instead',
            'of RealQuantity in QuakeML v1.2'
        ]
    },
    {
        qmlAttribute: 'inversionType',
        qmlType: 'bedt:MTInversionType [0..1]',
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
        qmlAttribute: 'iso',
        qmlType: 'co:RealQuantity [0..1]',
        qmlNested: [
            'value', 'undertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: [
            'Represented as pure float instead',
            'of RealQuantity in QuakeML v1.2'
        ]
    },
    {
        qmlAttribute: 'sourceTimeFunction',
        qmlType: 'bedt:SourceTimeFunction [0..1]',
        qmlNested: [
            'decayTime',
            'duration',
            'riseTime',
            'type (bedt:SourceTimeFunctionType)'
        ],
        qmlFormat: [
            'float', 'float', 'float', 'String (enum)'
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
        qmlAttribute: 'creationInfo',
        qmlType: 'rm:CreationInfo [0..1]',
        qmlNested: ['author', 'authorURI', 'agencyID', 'creationTime', 'version', 'copyrightOwner', 'copyrightOwnerURI', 'license'],
        qmlFormat: ['String', 'String', 'String', 'String', 'String', 'String', 'String', 'String'],
        qmlUnit: [],
        seisanLines: [
            '-', '-', 'M2'
        ],
        seisanFields: [
            '-', '-', 'Reporting Agency'
        ],
        seisanColumns: [
            '-', '-', '46-48'
        ],
        seisanFormat: [
            '-', '-', 'String'
        ],
        seisanUnit: [],
        comments: [
            '-', '-', 'From Qml to nordic -> first 3 char. Check for both author and agencyID to put into Agency code of the nordic format.'
        ]
      }
];

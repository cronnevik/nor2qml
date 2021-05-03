import { MappingAttributes } from 'src/app/shared/models/mappingattributes.model';

export const FocalMechanismAttributes: MappingAttributes[] = [
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
        qmlAttribute: 'waveformID',
        qmlType: 'wf:WaveformStreamID [0..*]',
        qmlNested: ['networkCode', 'stationCode', 'channelCode', 'locationCode', 'resourceURI'],
        qmlFormat: ['string', 'string', 'string', 'string', 'string'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'triggeringOriginID',
        qmlType: 'co:ResourceReference [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: ['Pointer to the related Origin entity. The previous Line 1 within seisan.']
    },
    {
        qmlAttribute: 'nodalPlanes',
        qmlType: ':bedt:NodalPlanes [0..1]',
        qmlNested: [
            'nodalPlane1 (bedt:NodalPlane [0..1]) => strike (co:RealQuantity) => value',
            'nodalPlane1 (bedt:NodalPlane [0..1]) => strike (co:RealQuantity) => uncertainty',
            'nodalPlane1 (bedt:NodalPlane [0..1]) => dip (co:RealQuantity) => value',
            'nodalPlane1 (bedt:NodalPlane [0..1]) => dip (co:RealQuantity) => uncertainty',
            'nodalPlane1 (bedt:NodalPlane [0..1]) => rake (co:RealQuantity) => value',
            'nodalPlane1 (bedt:NodalPlane [0..1]) => rake (co:RealQuantity) => uncertainty',
            '-',
            'nodalPlane2 (bedt:NodalPlane [0..1]) => strike (co:RealQuantity) => value',
            'nodalPlane2 (bedt:NodalPlane [0..1]) => strike (co:RealQuantity) => uncertainty',
            'nodalPlane2 (bedt:NodalPlane [0..1]) => dip (co:RealQuantity) => value',
            'nodalPlane2 (bedt:NodalPlane [0..1]) => dip (co:RealQuantity) => uncertainty',
            'nodalPlane2 (bedt:NodalPlane [0..1]) => rake (co:RealQuantity) => value',
            'nodalPlane2 (bedt:NodalPlane [0..1]) => rake (co:RealQuantity) => uncertainty',
        ],
        qmlFormat: [
            'float', 'float', 'float', 'float', 'float', 'float',
            '-',
            'float', 'float', 'float', 'float', 'float', 'float',
        ],
        qmlUnit: [
            'deg', 'deg', 'deg', 'deg', 'deg', 'deg',
            '-',
            'deg', 'deg', 'deg', 'deg', 'deg', 'deg',
        ],
        seisanLines: [
            'F', '-', 'F', '-', 'F', '-',
            '-',
            'F', '-', 'F', '-', 'F', '-',
        ],
        seisanFields: [
            'Strike', '-', 'Dip', '-', 'Rake', '-',
            '-',
            'Strike', '-', 'Dip', '-', 'Rake', '-',
        ],
        seisanColumns: [
            '1-10', '-', '11-20', '-', '21-30', '-',
            '-',
            '1-10', '-', '11-20', '-', '21-30', '-',
        ],
        seisanFormat: [
            'float', 'float', 'float', 'float', 'float', 'float',
            '-',
            'float', 'float', 'float', 'float', 'float', 'float',
        ],
        seisanUnit: [
            'deg', 'deg', 'deg', 'deg', 'deg', 'deg',
            '-',
            'deg', 'deg', 'deg', 'deg', 'deg', 'deg',
        ],
        comments: []
    },
    {
        qmlAttribute: 'principalAxes',
        qmlType: 'bedt:PrincipalAxes [0..1]',
        qmlNested: [
            'tAxis (bedt:Axis) => azimuth (co:RealQuantity) => Value',
            'tAxis (bedt:Axis) => azimuth (co:RealQuantity) => Uncertainty',
            'tAxis (bedt:Axis) => plunge (co:RealQuantity) => Value',
            'tAxis (bedt:Axis) => plunge (co:RealQuantity) => Uncertainty',
            'tAxis (bedt:Axis) => length (co:RealQuantity) => Value',
            'tAxis (bedt:Axis) => length (co:RealQuantity) => Uncertainty',
            '-',
            'pAxis (bedt:Axis) => azimuth (co:RealQuantity) => Value',
            'pAxis (bedt:Axis) => azimuth (co:RealQuantity) => Uncertainty',
            'pAxis (bedt:Axis) => plunge (co:RealQuantity) => Value',
            'pAxis (bedt:Axis) => plunge (co:RealQuantity) => Uncertainty',
            'pAxis (bedt:Axis) => length (co:RealQuantity) => Value',
            'pAxis (bedt:Axis) => length (co:RealQuantity) => Uncertainty',
            '-',
            'nAxis (bedt:Axis) => azimuth (co:RealQuantity) => Value',
            'nAxis (bedt:Axis) => azimuth (co:RealQuantity) => Uncertainty',
            'nAxis (bedt:Axis) => plunge (co:RealQuantity) => Value',
            'nAxis (bedt:Axis) => plunge (co:RealQuantity) => Uncertainty',
            'nAxis (bedt:Axis) => length (co:RealQuantity) => Value',
            'nAxis (bedt:Axis) => length (co:RealQuantity) => Uncertainty',
        ],
        qmlFormat: [
            'float', 'float', 'float', 'float', 'float', 'float',
            '-',
            'float', 'float', 'float', 'float', 'float', 'float',
            '-',
            'float', 'float', 'float', 'float', 'float', 'float',
        ],
        qmlUnit: [
            'deg', 'deg', 'deg', 'deg', 'Nm', 'Nm',
            '-',
            'deg', 'deg', 'deg', 'deg', 'Nm', 'Nm',
            '-',
            'deg', 'deg', 'deg', 'deg', 'Nm', 'Nm',
        ],
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
        seisanLines: ['F'],
        seisanFields: ['Program used'],
        seisanColumns: ['71-77'],
        seisanFormat: ['String'],
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
        qmlAttribute: 'misfit',
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
        qmlAttribute: 'stationDistributionRatio',
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
        qmlAttribute: 'stationPolarityCount',
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
            '-', '-', 'F'
        ],
        seisanFields: [
            '-', '-', 'Agency code'
        ],
        seisanColumns: [
            '-', '-', '67-69'
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

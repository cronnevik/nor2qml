import { MappingAttributes } from 'src/app/shared/models/mappingattributes.model';

export const ArrivalAttributes: MappingAttributes[] = [
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
        qmlAttribute: 'pickID',
        qmlType: 'co:ResourceReference [1..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: ['Pointer to the Pick entity']
    },
    {
        qmlAttribute: 'phase',
        qmlType: 'bedt:Phase [1..1]',
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
        qmlAttribute: 'timeCorrection',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: ['float'],
        qmlUnit: ['s'],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'azimuth',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: ['float'],
        qmlUnit: ['deg'],
        seisanLines: ['4'],
        seisanFields: ['Azimuth at source'],
        seisanColumns: ['77-79'],
        seisanFormat: ['int'],
        seisanUnit: ['deg'],
        comments: []
    },
    {
        qmlAttribute: 'distance',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: ['float'],
        qmlUnit: ['deg'],
        seisanLines: ['4'],
        seisanFields: ['Epicentral distance'],
        seisanColumns: ['71-75'],
        seisanFormat: ['float'],
        seisanUnit: ['km'],
        comments: ['Conversion between the units: deg and km']
    },
    {
        qmlAttribute: 'takeoffAngle',
        qmlType: 'co:RealQuantity [0..1]',
        qmlNested: [
            'value', 'undertainty', 'lowerUncertainty', 'upperUncertainty', 'confidenceLevel'
        ],
        qmlFormat: [
            'float', 'float', 'float', 'float', 'float',
        ],
        qmlUnit: [
            'deg', 'deg', 'deg', 'deg', '%'
        ],
        seisanLines: ['4'],
        seisanFields: ['Angle of incidence'],
        seisanColumns: ['57-60'],
        seisanFormat: ['float'],
        seisanUnit: ['deg'],
        comments: []
    },
    {
        qmlAttribute: 'timeResidual',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: ['float'],
        qmlUnit: ['s'],
        seisanLines: ['4'],
        seisanFields: ['Travel time residual'],
        seisanColumns: ['64-68'],
        seisanFormat: ['float'],
        seisanUnit: ['s'],
        comments: []
    },
    {
        qmlAttribute: 'horizontalSlownessResidual',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: ['float'],
        qmlUnit: ['s · deg−1'],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'backazimuthResidual',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: ['float'],
        qmlUnit: ['deg'],
        seisanLines: ['4'],
        seisanFields: ['Back azimuth residual'],
        seisanColumns: ['61-63'],
        seisanFormat: ['int'],
        seisanUnit: ['deg'],
        comments: []
    },
    {
        qmlAttribute: 'timeWeight',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: ['float'],
        qmlUnit: [],
        seisanLines: ['4'],
        seisanFields: ['Weighting Indicator'],
        seisanColumns: ['15'],
        seisanFormat: ['int'],
        seisanUnit: [],
        comments: [
            'Nordic => QuakeML',
            'blank => 1.0',
            '0 => 1.0',
            '1 => 0.75',
            '2 => 0.5',
            '3 => 0.25',
            '4 => 0.0'
        ]
    },
    {
        qmlAttribute: 'horizontalSlownessWeight',
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
        qmlAttribute: 'backazimuthWeight',
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
        qmlAttribute: 'earthModelID',
        qmlType: 'co:ResourceReference [0..1]',
        qmlNested: [],
        qmlFormat: ['String'],
        qmlUnit: [],
        seisanLines: ['1'],
        seisanFields: ['Distance Indicator'],
        seisanColumns: ['22'],
        seisanFormat: ['String'],
        seisanUnit: [],
        comments: [
            'Nordic => QuakeML',
            'D => IASP91',
            'L => Local model',
            'R => Unknown (default value back to seisan)'
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

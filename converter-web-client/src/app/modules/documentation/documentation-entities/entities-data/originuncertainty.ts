import { MappingAttributes } from "src/app/shared/models/mappingattributes.model";

export const OriginUncertaintyAttributes: MappingAttributes[] = [
    {
        qmlAttribute: 'preferredDescription',
        qmlType: ':bedt:OriginUncertaintyDescription [0..1]',
        qmlNested: [],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'horizontalUncertainty',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'maxHorizontalUncertainty',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'minHorizontalUncertainty',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'azimuthMaxHorizontalUncertainty',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    },
    {
        qmlAttribute: 'confidenceEllipsoid',
        qmlType: ':bedt:ConfidenceEllipsoid [0..1]',
        qmlNested: [
            'semiMajorAxisLength',
            'semiMinorAxisLength',
            'semiIntermediateAxisLength',
            'majorAxisPlunge',
            'majorAxisAzimuth',
            'majorAxisRotation'
        ],
        qmlFormat: [
            'float', 'float', 'float', 'float', 'float', 'float'
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
        qmlAttribute: 'confidenceLevel',
        qmlType: 'float [0..1]',
        qmlNested: [],
        qmlFormat: [],
        qmlUnit: [],
        seisanLines: [],
        seisanFields: [],
        seisanColumns: [],
        seisanFormat: [],
        seisanUnit: [],
        comments: []
    }
];

import { MappingAttributes } from 'src/app/shared/models/mappingattributes.model';

export const StationMagnitudeContributionAttributes: MappingAttributes[] = [
    {
        qmlAttribute: 'publicID',
        qmlType: 'co:ResourceReference [1..1]',
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
        qmlAttribute: 'residual',
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
        qmlAttribute: 'weight',
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
];

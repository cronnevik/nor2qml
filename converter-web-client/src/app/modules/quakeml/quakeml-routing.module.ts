import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QuakemlComponent } from './quakeml.component';
import { QmlWebServiceComponent } from './qml-web-service/qml-web-service.component';
import { QmlXmlTextComponent } from './qml-xml-text/qml-xml-text.component';
import { QmlXmlFileComponent } from './qml-xml-file/qml-xml-file.component';
import { QmlViewSfilesComponent } from './qml-view-sfiles/qml-view-sfiles.component';

const quakemlRoutes: Routes = [
    { path: '', component: QuakemlComponent, children: [
        { path: '', redirectTo: 'web-service', pathMatch: 'full' },
        { path: 'web-service', component: QmlWebServiceComponent },
        { path: 'xml-text', component: QmlXmlTextComponent },
        { path: 'xml-file', component: QmlXmlFileComponent },
        { path: 'view-sfiles', component: QmlViewSfilesComponent}
    ]}
];

@NgModule({
    imports: [
        RouterModule.forChild(quakemlRoutes)
    ],
    exports: [RouterModule]
})
export class QuakemlRoutingModule {}

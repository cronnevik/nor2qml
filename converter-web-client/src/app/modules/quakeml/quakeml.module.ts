import { NgModule } from '@angular/core';
import { QuakemlComponent } from './quakeml.component';
import { QuakemlRoutingModule } from './quakeml-routing.module';
import { FormsModule } from '@angular/forms';
import { QmlWebServiceComponent } from './qml-web-service/qml-web-service.component';
import { QmlXmlTextComponent } from './qml-xml-text/qml-xml-text.component';
import { QmlXmlFileComponent } from './qml-xml-file/qml-xml-file.component';
import { AngularFileUploaderModule } from 'angular-file-uploader';
import { QmlToSfileService } from 'src/app/core/http/qml-to-sfile.service';
import { HttpClientModule } from '@angular/common/http';
import { NgxLoadingModule } from 'ngx-loading';
import { QmlViewSfilesComponent } from './qml-view-sfiles/qml-view-sfiles.component';
import { ApPrismModule } from '@angular-package/prism/core';
import { CommonModule } from '@angular/common';

@NgModule({
    declarations: [
        QuakemlComponent,
        QmlWebServiceComponent,
        QmlXmlTextComponent,
        QmlXmlFileComponent,
        QmlViewSfilesComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        HttpClientModule,
        ApPrismModule,
        AngularFileUploaderModule,
        NgxLoadingModule.forRoot({}),
        QuakemlRoutingModule
    ],
    providers: [
        QmlToSfileService
    ]
})
export class QuakemlModule {}

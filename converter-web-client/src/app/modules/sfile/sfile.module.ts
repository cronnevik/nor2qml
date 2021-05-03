import { NgModule } from '@angular/core';
import { SfileComponent } from './sfile.component';
import { CommonModule } from '@angular/common';
import { ApPrismModule } from '@angular-package/prism/core';
import { AngularFileUploaderModule } from 'angular-file-uploader';
import { SfileRoutingModule } from './sfile-routing.module';
import { SfileLinesComponent } from './sfile-lines/sfile-lines.component';
import { SfileToQmlService } from 'src/app/core/http/sfile-to-qml.service';
import { HttpClientModule } from '@angular/common/http';
import { SfileXmlViewComponent } from './sfile-xml-view/sfile-xml-view.component';
import { NgxLoadingModule } from 'ngx-loading';
import { FormsModule } from '@angular/forms';
import { FileUploadModule } from 'ng2-file-upload';

@NgModule({
    declarations: [
        SfileComponent,
        SfileLinesComponent,
        SfileXmlViewComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ApPrismModule,
        AngularFileUploaderModule,
        NgxLoadingModule.forRoot({}),
        HttpClientModule,
        SfileRoutingModule,
        FileUploadModule
    ],
    providers: [
        SfileToQmlService,
    ]
})
export class SfileModule {}

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SfileComponent } from './sfile.component';
import { SfileLinesComponent } from './sfile-lines/sfile-lines.component';
import { SfileXmlViewComponent } from './sfile-xml-view/sfile-xml-view.component';

const sfileRoutes: Routes = [
    { path: '', component: SfileComponent },
    { path: 'lines', component: SfileLinesComponent },
    { path: 'xml-download', component: SfileXmlViewComponent }

];

@NgModule({
    imports: [
        RouterModule.forChild(sfileRoutes)
    ],
    exports: [RouterModule]
})
export class SfileRoutingModule {}

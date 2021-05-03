import { RouterModule, Routes } from '@angular/router';
import { DocumentationComponent } from './documentation.component';
import { NgModule } from '@angular/core';

const docRoutes: Routes = [
    { path: '', component: DocumentationComponent }
];

@NgModule({
    imports: [
        RouterModule.forChild(docRoutes)
    ],
    exports: [RouterModule]
})
export class DocumentationRoutingModule {}

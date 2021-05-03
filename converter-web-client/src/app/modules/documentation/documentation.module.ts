import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentationComponent } from './documentation.component';
import { DocumentationRoutingModule } from './documentation-routing.module';
import { DocumentationEntityComponent } from './documentation-entities/documentation-entity/documentation-entity.component';
import { DocumentationEntitiesComponent } from './documentation-entities/documentation-entities.component';

@NgModule({
    declarations: [
        DocumentationComponent,
        DocumentationEntityComponent,
        DocumentationEntitiesComponent
    ],
    imports: [
        CommonModule,
        DocumentationRoutingModule
    ],
    providers: []
})
export class DocumentationModule {

}

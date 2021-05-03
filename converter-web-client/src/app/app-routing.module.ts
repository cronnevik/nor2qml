import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './core/home/home.component';

const docModule = './modules/documentation/documentation.module#DocumentationModule';
const sfileModule = './modules/sfile/sfile.module#SfileModule';
const quakemlModule = './modules/quakeml/quakeml.module#QuakemlModule';

const appRoutes: Routes = [
    { path: '', component: HomeComponent, pathMatch: 'full' },
    { path: 'documentation', loadChildren: docModule },
    { path: 'sfile-to-quakeml', loadChildren: sfileModule },
    { path: 'quakeml-to-sfile', loadChildren: quakemlModule }
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}

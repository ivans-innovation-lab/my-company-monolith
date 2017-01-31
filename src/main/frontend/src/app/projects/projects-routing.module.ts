import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProjectsComponent } from './projects.component';
import { ProjectComponent } from './project/project.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectNewComponent } from './project-new/project-new.component';

const projectsRoutes: Routes = [
  {
    path: 'projects',
    component: ProjectsComponent,
    children: [
      {
        path: '',
        component: ProjectListComponent,
        children: [
          {
            path: ':id',
            component: ProjectComponent
          },
          {
            path: 'action/new',
            component: ProjectNewComponent
          }
        ]
      }
    ]
  }
];
@NgModule({
  imports: [
    RouterModule.forChild(projectsRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class ProjectsRoutingModule {

}

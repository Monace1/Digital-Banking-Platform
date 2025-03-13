import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { ManageCustomersComponent } from './manage-customers/manage-customers.component';
import { ManageAccountsComponent } from './manage-accounts/manage-accounts.component';
import { ManageTransactionsComponent } from './manage-transactions/manage-transactions.component';
import { AuditLogsComponent } from './audit-logs/audit-logs.component';


@NgModule({
  declarations: [
    ManageCustomersComponent,
    ManageAccountsComponent,
    ManageTransactionsComponent,
    AuditLogsComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }

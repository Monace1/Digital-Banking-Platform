import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManageCustomersComponent } from './manage-customers/manage-customers.component';
import { ManageAccountsComponent } from './manage-accounts/manage-accounts.component';
import { ManageTransactionsComponent } from './manage-transactions/manage-transactions.component';
import { AuditLogsComponent } from './audit-logs/audit-logs.component';

const routes: Routes = [
  { path: 'manage-customers', component: ManageCustomersComponent },
  { path: 'manage-accounts', component: ManageAccountsComponent },
  { path: 'manage-transactions', component: ManageTransactionsComponent },
  { path: 'audit-logs', component: AuditLogsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}

import { Routes } from '@angular/router';
import { HomePage } from '../pages/home-page/home-page';
import { StockPage } from '@app/pages/stock-page/stock-page';
import { ProfilPage } from '@app/pages/profil-page/profil-page';
import { GamblePage } from '@app/pages/gamble-page/gamble-page';

export const routes: Routes = [
    {path: '', redirectTo: 'home', pathMatch: 'full'},
    {path: 'home', component:HomePage},
    {path: 'stock', component: StockPage},
    {path: 'profile', component: ProfilPage},
    {path: 'gamble', component: GamblePage}
];

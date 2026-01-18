import { Component } from '@angular/core';
import { Separator } from '@app/components/separator/separator';
import { StockTable } from "@app/components/stock-table/stock-table";


@Component({
  selector: 'app-home-page',
  imports: [Separator, StockTable],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {

}

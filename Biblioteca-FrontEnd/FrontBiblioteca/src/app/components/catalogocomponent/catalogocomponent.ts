import { Component } from '@angular/core';
import { CatalogoListar } from './catalogo-listar/catalogo-listar';

@Component({
  selector: 'app-catalogocomponent',
  standalone: true,
  imports: [CatalogoListar],
  templateUrl: './catalogocomponent.html',
  styleUrls: ['./catalogocomponent.css'],
})
export class Catalogocomponent {}

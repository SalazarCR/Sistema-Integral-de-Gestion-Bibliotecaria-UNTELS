import { Routes } from '@angular/router';
import { Homecomponent } from './components/homecomponent/homecomponent';
import { Librocomponent } from './components/librocomponent/librocomponent';
import { Usuariocomponent } from './components/usuariocomponent/usuariocomponent';
import { Estudiantecomponent } from './components/estudiantecomponent/estudiantecomponent';
import { Prestamocomponent } from './components/prestamocomponent/prestamocomponent';
import { Catalogocomponent } from './components/catalogocomponent/catalogocomponent';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'homes',
        pathMatch: 'full'
    },
    {
        path: 'homes',
        component: Homecomponent
    },
    {
        path: 'libros',
        component: Librocomponent
    },
    {
        path: 'usuarios',
        component: Usuariocomponent
    },
    {
        path: 'estudiantes',
        component: Estudiantecomponent
    },
    {
        path: 'prestamos',
        component: Prestamocomponent
    },
    {
        path: 'catalogo',
        component: Catalogocomponent
    },
    {
        path: '**',
        redirectTo: 'homes'
    }
];

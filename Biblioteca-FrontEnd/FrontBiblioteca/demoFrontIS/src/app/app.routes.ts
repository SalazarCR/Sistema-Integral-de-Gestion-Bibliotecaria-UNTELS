import { Routes } from '@angular/router';
import { Homecomponent } from './components/homecomponent/homecomponent';
import { Proveedorcomponent } from './components/proveedorcomponent/proveedorcomponent';
import { ProveedorListar } from './components/proveedorcomponent/proveedor-listar/proveedor-listar';

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
        path:'providers',
        component:Proveedorcomponent,
        children:[
            {
                path:'list',
                component:ProveedorListar
            }
        ]
    }
];

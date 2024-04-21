/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This is for bootstrapping an Angular application.
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import required modules
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

// Import the root module of the application
import { AppModule } from './app/app.module';

// Import environment configuration
import { environment } from './environments/environment';

// Enable production mode if environment is production
if (environment.production) {
  enableProdMode();
}

// Bootstrap the application module
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));


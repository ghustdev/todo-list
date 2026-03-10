import './style.css'
import { loadState } from './state.js'
import { render } from './render.js'
import { startAlertSystem } from './alerts.js'

// Initialize app
loadState()
render()
startAlertSystem()

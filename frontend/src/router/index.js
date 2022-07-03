import { createRouter, createWebHistory } from "vue-router";
import Translate from "../pages/Translate.vue";
import About from "../pages/About.vue";
import Contribute from "../pages/Contribuir.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: Translate,
  },
  {
    path: "/sobre",
    name: "about",
    component: About,
  },
  {
    path: "/contribuir",
    name: "contribuir",
    component: Contribute,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

import { useIntersectionObserver } from "@vueuse/core";
import { App, Directive } from "vue";

export default function setupAnimate(app:App) {

	const animateDirective: Directive<HTMLElement, string> = {
		mounted(el: HTMLElement, binding) {
			const { value } = binding;
			const { stop } = useIntersectionObserver(el, ([{ isIntersecting }]) => {
				// 元素进入可视区
				if (isIntersecting) {
					stop();
					const { top } = el.getBoundingClientRect();
					const h =
						document.documentElement.clientHeight || document.body.clientHeight;
					if (top < h) {
						el.style.visibility = "visible";
						el.style.animationName = value;
					}
				}
			});
		}
	};

	app.directive("animate", animateDirective);
}

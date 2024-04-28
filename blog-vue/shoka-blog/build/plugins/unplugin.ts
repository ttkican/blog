import path from "node:path";
import AutoImport from "unplugin-auto-import/vite";
import { FileSystemIconLoader } from "unplugin-icons/loaders";
import IconsResolver from "unplugin-icons/resolver";
import Icons from "unplugin-icons/vite";
import { NaiveUiResolver } from "unplugin-vue-components/resolvers";
import Components from "unplugin-vue-components/vite";
import { prismjsPlugin } from "vite-plugin-prismjs";
import { createSvgIconsPlugin } from "vite-plugin-svg-icons";

export default function unplugin(viteEnv: Env.ImportMeta) {
	const { VITE_ICON_PREFIX, VITE_ICON_LOCAL_PREFIX } = viteEnv;

	const localIconPath = path.join(process.cwd(), "src/assets/icons");
	/** 本地svg图标集合名称 */
	const collectionName = VITE_ICON_LOCAL_PREFIX.replace(
		`${VITE_ICON_PREFIX}-`,
		""
	);

	return [
		Icons({
			compiler: "vue3",
			customCollections: {
				[collectionName]: FileSystemIconLoader(localIconPath, (svg) =>
					svg.replace(/^<svg\s/, '<svg width="1em" height="1em" ')
				),
			},
			scale: 1,
			defaultClass: "inline-block",
		}),

		AutoImport({
			imports: ["vue", "vue-router", "pinia"],
			dts: "src/types/auto-imports.d.ts",
		}),
		Components({
			dts: "src/types/components.d.ts",
			resolvers: [
				NaiveUiResolver(),
				IconsResolver({
					customCollections: [collectionName],
					componentPrefix: VITE_ICON_PREFIX,
				}),
			],
		}),
		createSvgIconsPlugin({
			// 指定需要缓存的图标文件夹
			iconDirs: [localIconPath],
			// 指定symbolId格式
			symbolId: "icon-[dir]-[name]",
		}),
		prismjsPlugin({
			languages: [
				"java",
				"python",
				"html",
				"css",
				"sass",
				"less",
				"go",
				"cpp",
				"c",
				"js",
				"ts",
				"sql",
				"bash",
				"git",
				"nginx",
				"php",
			],
			theme: "tomorrow",
			css: true,
		}),
	];
}

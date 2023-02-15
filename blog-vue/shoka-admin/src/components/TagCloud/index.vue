<template>
    <div class="cloud_wrap">
        <div class="tagcloud-all">
            <a class="tag" v-for="(item, index) in tagList" :key="index" rel="external nofollow"
                :style="'color:' + item.color + ';top: 0;left: 0;filter:none;'">{{ item.tagName }}</a>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { nextTick, onMounted, reactive } from 'vue';
const props = defineProps({
    tagList: {
        type: Array<any>,
        default: [],
    }
});
const config = reactive<any>({
    radius: 120,
    dtr: Math.PI / 180,
    d: 300,
    mcList: [],
    active: false,
    lasta: 1,
    lastb: 1,
    distr: true,
    tspeed: 5, // 控制旋转速度
    size: 250,
    mouseX: 0,
    mouseY: 0,
    howElliptical: 1,
    oList: null,
    oA: null,
    sa: 0,
    ca: 0,
    sb: 0,
    cb: 0,
    sc: 0,
    cc: 0
});

// 生成随机数
function getRandomNum() {
    return Math.floor(Math.random() * (255 + 1));
};
// 三角函数角度计算
function sineCosine(a: number, b: number, c: number) {
    config.sa = Math.sin(a * config.dtr);
    config.ca = Math.cos(a * config.dtr);
    config.sb = Math.sin(b * config.dtr);
    config.cb = Math.cos(b * config.dtr);
    config.sc = Math.sin(c * config.dtr);
    config.cc = Math.cos(c * config.dtr);
}
// 设置初始定位
function positionAll() {
    nextTick(() => {
        let phi = 0;
        let theta = 0;
        let max = config.mcList.length;
        let aTmp = [];
        let oFragment = document.createDocumentFragment();
        // 随机排序
        for (let i = 0; i < props.tagList.length; i++) {
            aTmp.push(config.oA[i]);
        }
        aTmp.sort(() => {
            return Math.random() < 0.5 ? 1 : -1;
        });
        for (let i = 0; i < aTmp.length; i++) {
            oFragment.appendChild(aTmp[i]);
        }
        config.oList.appendChild(oFragment);
        for (let i = 1; i < max + 1; i++) {
            if (config.distr) {
                phi = Math.acos(-1 + (2 * i - 1) / max);
                theta = Math.sqrt(max * Math.PI) * phi;
            } else {
                phi = Math.random() * (Math.PI);
                theta = Math.random() * (2 * Math.PI);
            }
            // 坐标变换
            config.mcList[i - 1].cx = config.radius * Math.cos(theta) * Math.sin(phi);
            config.mcList[i - 1].cy = config.radius * Math.sin(theta) * Math.sin(phi);
            config.mcList[i - 1].cz = config.radius * Math.cos(phi);
            config.oA[i - 1].style.left = config.mcList[i - 1].cx + config.oList.offsetWidth / 2 - config.mcList[i - 1].offsetWidth / 2 + 'px';
            config.oA[i - 1].style.top = config.mcList[i - 1].cy + config.oList.offsetHeight / 2 - config.mcList[i - 1].offsetHeight / 2 + 'px';
        }
    })
}
// 坐标更新 让标签动起来
function update() {
    nextTick(() => {
        let a;
        let b;
        if (config.active) {
            a = (-Math.min(Math.max(-config.mouseY, -config.size), config.size) / config.radius) * config.tspeed;
            b = (Math.min(Math.max(-config.mouseX, -config.size), config.size) / config.radius) * config.tspeed;
        } else {
            a = config.lasta * 0.98;
            b = config.lastb * 0.98;
        }
        config.lasta = a;
        config.lastb = b;
        if (Math.abs(a) <= 0.01 && Math.abs(b) <= 0.01) {
            return
        }
        let c = 0;
        sineCosine(a, b, c);
        for (let j = 0; j < config.mcList.length; j++) {
            let rx1 = config.mcList[j].cx;
            let ry1 = config.mcList[j].cy * config.ca + config.mcList[j].cz * (-config.sa);
            let rz1 = config.mcList[j].cy * config.sa + config.mcList[j].cz * config.ca;
            let rx2 = rx1 * config.cb + rz1 * config.sb;
            let ry2 = ry1;
            let rz2 = rx1 * (-config.sb) + rz1 * config.cb;
            let rx3 = rx2 * config.cc + ry2 * (-config.sc);
            let ry3 = rx2 * config.sc + ry2 * config.cc;
            let rz3 = rz2;
            config.mcList[j].cx = rx3;
            config.mcList[j].cy = ry3;
            config.mcList[j].cz = rz3;
            let per = config.d / (config.d + rz3);
            config.mcList[j].x = (config.howElliptical * rx3 * per) - (config.howElliptical * 2);
            config.mcList[j].y = ry3 * per;
            config.mcList[j].scale = per;
            config.mcList[j].alpha = per;
            config.mcList[j].alpha = (config.mcList[j].alpha - 0.6) * (10 / 6);
        }
        doPosition();
        depthSort();
    })
}
function doPosition() {
    nextTick(() => {
        let l = config.oList.offsetWidth / 2;
        let t = config.oList.offsetHeight / 2;
        for (let i = 0; i < config.mcList.length; i++) {
            config.oA[i].style.left = config.mcList[i].cx + l - config.mcList[i].offsetWidth / 2 + 'px';
            config.oA[i].style.top = config.mcList[i].cy + t - config.mcList[i].offsetHeight / 2 + 'px';
            config.oA[i].style.fontSize = Math.ceil(12 * config.mcList[i].scale / 2) + 8 + 'px';
            // config.oA[i].style.filter = "alpha(opacity=" + 100 * config.mcList[i].alpha + ")";
            config.oA[i].style.opacity = config.mcList[i].alpha;
        }
    })
}
function depthSort() {
    nextTick(() => {
        const aTmp = [];
        for (let i = 0; i < config.oA.length; i++) {
            aTmp.push(config.oA[i]);
        }
        aTmp.sort(function (vItem1, vItem2) {
            if (vItem1.cz > vItem2.cz) {
                return -1;
            } else if (vItem1.cz < vItem2.cz) {
                return 1;
            } else {
                return 0;
            }
        });
        for (let i = 0; i < aTmp.length; i++) {
            aTmp[i].style.zIndex = i;
        }
    })
}
// 生成随机颜色
function query() {
    if (!Array.isArray(props.tagList)) {
        return;
    }
    // 给tagList添加随机颜色
    props.tagList.forEach((item: any) => {
        item.color = "rgb(" + getRandomNum() + "," + getRandomNum() + "," + getRandomNum() + ")";
    })
    onReady();
}
// 生成标签云
function onReady() {
    nextTick(() => {
        config.oList = document.querySelector('.tagcloud-all');
        config.oA = config.oList.getElementsByTagName('a')
        for (var i = 0; i < config.oA.length; i++) {
            const oTag: any = {};
            oTag.offsetWidth = config.oA[i].offsetWidth;
            oTag.offsetHeight = config.oA[i].offsetHeight;
            config.mcList.push(oTag);
        }
        sineCosine(0, 0, 0);
        positionAll();
        config.oList.onmouseover = () => {
            config.active = true;
        }
        config.oList.onmouseout = () => {
            config.active = false;
        }
        config.oList.onmousemove = (event: any) => {
            const oEvent: any = window.event || event;
            config.mouseX = oEvent.clientX - (config.oList.offsetLeft + config.oList.offsetWidth / 2);
            config.mouseY = oEvent.clientY - (config.oList.offsetTop + config.oList.offsetHeight / 2);
            config.mouseX /= 5;
            config.mouseY /= 5;
        }
        setInterval(() => {
            update()
        }, 30);
    })
};

onMounted(() => {
    nextTick(() => {
        query();
    });
})
</script>

<style lang="scss" scoped>
.cloud_wrap {
    width: 100%;
    height: 350px;

    // 标签云
    .tagcloud-all {
        height: 100%;
        position: relative;

        .tag {
            position: absolute;
            top: 0px;
            left: 0px;
            color: #fff;
            font-weight: bold;
            text-decoration: none;
            padding: 3px 6px;

            &:hover {
                color: #FF0000;
                letter-spacing: 2px;
            }
        }
    }
}
</style>
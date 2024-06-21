<template>
  <div>
    <div class="card" style="padding: 15px">
      您好，{{user.name}}！欢迎使用本系统
    </div>
    <div style="display: flex; grid-gap: 10px; margin: 10px 0">
      <div class="card" style="flex: 1; display: flex; align-items: center">
        <img src="@/assets/imgs/仓储.png" alt="" style="width: 50px">
        <div style="margin-left: 40px">
          <div style="color: #666">商品种类</div>
          <div style="margin-top: 5px; font-size: 20px">{{ dataCount.goodsNum }}</div>
        </div>
      </div>
      <div class="card" style="flex: 1; display: flex; align-items: center">
        <img src="@/assets/imgs/库存余额.png" alt="" style="width: 50px">
        <div style="margin-left: 40px">
          <div style="color: #666">销售金额</div>
          <div style="margin-top: 5px; font-size: 20px">{{ dataCount.totalSalePrice }}</div>
        </div>
      </div>
      <div class="card" style="flex: 1; display: flex; align-items: center">
        <img src="@/assets/imgs/采购.png" alt="" style="width: 50px">
        <div style="margin-left: 40px">
          <div style="color: #666">采购订单</div>
          <div style="margin-top: 5px; font-size: 20px">{{ dataCount.stockOrdersNum }}</div>
        </div>
      </div>
      <div class="card" style="flex: 1; display: flex; align-items: center">
        <img src="@/assets/imgs/销售.png" alt="" style="width: 50px">
        <div style="margin-left: 40px">
          <div style="color: #666">销售订单</div>
          <div style="margin-top: 5px; font-size: 20px">{{ dataCount.saleOrdersNum }}</div>
        </div>
      </div>
    </div>

    <div style="margin-top: 30px; display: flex; grid-gap: 10px">
      <div style="flex: 1" class="card">
        <div style="height: 450px" id="line"></div>
      </div>

      <div style="flex: 1" class="card">
        <div style="height: 450px" id="pie"></div>
      </div>
    </div>

  </div>
</template>

<script>
import * as echarts from 'echarts'
import {getData,getSaleNearlyWeek,getStoreCount} from '@/api/dataCount'
const lineOption = {
  title: {
    text: '近一周商品销售额',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    left: 'left'
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '金额',
      data: [],
      type: 'line',
      smooth: true
    },
  ]
}


const pieOption = {
  title: {
    text: '库存占比统计',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      type: 'pie',
      stillShowZeroSum: false,
      center: ['50%', '60%'],
      radius: '50%',
      data: [],
      label: {
        show: false,
        formatter(param) {
          return param.name + ' (' + param.percent + '%)';
        }
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}

export default {
  name: 'Home',
  // 折线图  必须是 mounted
  async mounted() {
    // 折线图
    let linetDom = document.getElementById('line')
    let lineChart = echarts.init(linetDom)
    let result = await getSaleNearlyWeek()
    lineOption.xAxis.data = result.data.map(v => v.date)
    lineOption.series[0].data = result.data.map(v => v.price)
    lineChart.setOption(lineOption)

    // 饼图
    let pieDom = document.getElementById('pie')
    let pieChart = echarts.init(pieDom)
    let res = await getStoreCount()
    console.log(res.data)
    pieOption.series[0].data = res.data

    pieChart.setOption(pieOption)
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      dataCount:{}
    }
  },
  created() {
    this.getDataCount()
  },
  methods:{
    async getDataCount(){
      let result = await getData()
      this.dataCount = result.data
    },

    async getWeekSale(){
      let result = await getSaleNearlyWeek()

    }
  }
}
</script>

<style>
.card {
  background-color: #fff;
  border-radius: 5px;
  padding: 20px;
  box-shadow: 0 0 8px rgba(0,0,0,.1);
}
</style>

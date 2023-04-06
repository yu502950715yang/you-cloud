<script>
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    },
    isElIcon: {
      type: Boolean,
      default: false
    }
  },
  render(h, context) {
    const { icon, title, isElIcon } = context.props
    const vnodes = []

    if (icon) {
      if (isElIcon) {
        vnodes.push(<i class={icon}></i>)
      } else {
        vnodes.push(<svg-icon icon-class={icon}/>)
      }
    }

    if (title) {
      vnodes.push(<span slot='title'>{(title)}</span>)
    }
    return vnodes
  }
}
</script>

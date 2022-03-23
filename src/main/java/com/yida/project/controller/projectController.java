package com.yida.project.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yida.project.entity.*;
import com.yida.project.exception.R;
import com.yida.project.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 主界面控制器
 */
@RequestMapping("/main")
@Controller
public class projectController {
    //配置上传文件的目录
    public String uploadFolder = "public" + File.separator;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private ScenicService scenicService;

    @Autowired
    private ReleasingNoticesService releasingNoticesService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ServeService serveService;

    @Autowired
    private ServetwoService servetwoService;

    @Autowired
    private ServerthreeService serverthreeService;

    @Autowired
    private SetupService setupService;

    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private PeripheralService peripheralService;

    @Autowired(required = false)
    private SetupTwoService setupTwoService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    //主界面
    @GetMapping("/indexs")
    public String login2(HttpSession session,Model model) {
        Subject subject = SecurityUtils.getSubject();//获取主题对象
        String username = (String) subject.getPrincipal();
        session.setAttribute("username", username);
        model.addAttribute("port", port);
        return "main";
    }

    @GetMapping("/base")
    public String base() {
        return "base";
    }

    //注册用户
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @GetMapping("/adduserpage")
    public String adduserpage() {
        return "add";
    }

    @PostMapping("/addUser")
    @Transactional
    public String adduser(@RequestParam("name") String name, @RequestParam("password") String password) {
        userService.createuser(name, password);
        // redirect:main
        return "redirect:/main/selectAllUsers";
    }

    @GetMapping("/selectAllUsers")//查询所有用户信息
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String selectallusers(Model model) {
        List<User> users = userService.selectAllusers();
        model.addAttribute("users", users);
        model.addAttribute("port", port);
        return "find";
    }

    //管理员界面修改用户信息界面
    @GetMapping("/adupdate")
    @RequiresRoles(value = {"admin"})
    @RequiresPermissions(value = {"admin:select:*"})
    public String updateuser(@RequestParam("id") Integer id, Model model) {
        User user = userService.selectone(id);
        model.addAttribute("user", user);
        return "updateuser";
    }

    //管理员修改用户提交页面
    @PostMapping("/updateone")
    @RequiresRoles(value = {"admin"})
    @RequiresPermissions(value = {"admin:select:*", "admin:update:*"})
    public String updatead(User user) {
        userService.updateuser(user);
        return "redirect:/main/selectAllUsers";
    }

    //管理员删除指定用户
    @GetMapping("/addelete")
    @RequiresRoles(value = {"admin"})
    @RequiresPermissions(value = {"admin:select:*", "admin:delete:*"})
    public String deleteddd(@RequestParam("id") Integer id) {
        User user = userService.user_select(id);//通过用户ID获取此用户在中间表中的id
        System.out.println("用户引用的中间表的ID=" + user.getId());
        userRoleService.delete(user.getId());//删除中间表中此用户关联的数据
        userService.deleteon(id);      //删除用户      引用了外键的表，必须先删除中间表数据才能删除当前表中数据
        return "redirect:/main/selectAllUsers";
    }


    //修改个人用户信息界面
    @GetMapping("/updateuser")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})
    public String update(Model model) {
        Subject subject = SecurityUtils.getSubject();//获取主题对象
        //获取主题名称
        String username = (String) subject.getPrincipal();
        System.out.println(username);
        User user = userService.selectName(username);
        model.addAttribute("user", user);
        model.addAttribute("port", port);
        return "update";
    }

    //修改
    @ResponseBody//以JSON字符串的方式返回数据给前端
    @RequiresPermissions(value = {"admin:select:*", "admin:update:*"})
    @PostMapping("/updateuser")
    @Transactional
    public R updateuser(User user) {
        userService.updateuser(user);
        return R.ok("修改成功");
    }

    //景点列表主界面展示
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    @GetMapping("/scennic")
    public String scennicl(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        List<Scenic> sec = scenicService.SeleteAll();
        PageHelper.startPage(page, 1);
        List<ReleasingNotices> list = releasingNoticesService.ReadAll();
        PageInfo<ReleasingNotices> pageinfo = new PageInfo<>(list);
        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages >= 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("RNS", list);
        model.addAttribute("page", page);
        model.addAttribute("scenics", sec);
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }

        model.addAttribute("port", port);
        return "scennic";
    }


    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    @RequestMapping(value = "/scennic", method = RequestMethod.POST)
    public String scennic(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        List<Scenic> sec = scenicService.SeleteAll();
        PageHelper.startPage(page, 1);
        List<ReleasingNotices> list = releasingNoticesService.ReadAll();
        PageInfo<ReleasingNotices> pageinfo = new PageInfo<>(list);

        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages > 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("RNS", list);
        model.addAttribute("page", page);
        model.addAttribute("scenics", sec);
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        return "scennic";
    }

    @RequiresRoles(value = {"admin"})
    @GetMapping("/scennic_1")
    public String scennic_1(Model model) {
        List<Scenic> sec = scenicService.SeleteAll();
        model.addAttribute("scenics", sec);
        return "scennic_1";
    }

    // 景区发布页面
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @PostMapping("/scennic2")
    public String scennic2(@RequestParam("spotImages") String imgs, @RequestParam("yida2") MultipartFile file, ReleasingNotices res, HttpServletRequest request) throws Exception {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fo = sim.format(res.getModifyTime());
        Date pars = sim.parse(fo);
        res.setModifyTime(pars);
        res.setSpotImages(imgs);
        String Filename = addfile(file);
        String path = "/img" + File.separator;
        String str = path + Filename; //组合c
        res.setThumbnail(str); //保存到数据库
        releasingNoticesService.add(res);
        return "redirect:/main/scennic";
    }

    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    @RequestMapping(value = "/readall", method = {RequestMethod.GET})
    public String scennic0(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        List<Scenic> sec = scenicService.SeleteAll();
        PageHelper.startPage(page, 1);
        List<ReleasingNotices> list = releasingNoticesService.ReadAll();
        PageInfo<ReleasingNotices> pageinfo = new PageInfo<>(list);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("RNS", list);
        model.addAttribute("scenics", sec);
        return "scennic";
    }

    /* 景点搜索按钮*/
    @PostMapping("readall")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String read(@RequestParam("title") String ByName, @RequestParam("scenic.id") Integer cid, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) throws Exception {
        List<Scenic> sec = scenicService.SeleteAll();//景区选择显示
        Scenic read = scenicService.selecton(cid);
        String name = "%" + ByName.trim() + "%";
        //分页
        PageHelper.startPage(page, 1);
        List<ReleasingNotices> emp = releasingNoticesService.Readrn(name, cid, begin, end);
        PageInfo<ReleasingNotices> pageinfo = new PageInfo<>(emp);

        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages > 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("RNS", emp);
        model.addAttribute("nameby", ByName);
        model.addAttribute("scenics", sec);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        model.addAttribute("page", page);
        if (read != null) {
            model.addAttribute("scen", read.getName());
        }
        model.addAttribute("id", cid);
        return "scennic1";
    }

    /* 搜索分页按钮*/
    @GetMapping("readall2")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String read2(@RequestParam("title") String ByName, @RequestParam("scenic.id") Integer cid, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) throws Exception {
        List<Scenic> sec = scenicService.SeleteAll();//景区选择显示
        Scenic read = scenicService.selecton(cid);
        String name = "%" + ByName.trim() + "%";
        //分页
        PageHelper.startPage(page, 1);
        List<ReleasingNotices> emp = releasingNoticesService.Readrn(name, cid, begin, end);
        PageInfo<ReleasingNotices> pageinfo = new PageInfo<>(emp);

        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages >= 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("RNS", emp);
        model.addAttribute("nameby", ByName);
        model.addAttribute("scenics", sec);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        model.addAttribute("page", page);
        if (read != null) {
            model.addAttribute("scen", read.getName());
        }
        model.addAttribute("id", cid);
        return "scennic1";
    }


    //景点修改界面
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateon(@RequestParam("id") Integer id1, @RequestParam("page") Integer page, Model model) {
        ReleasingNotices res = releasingNoticesService.selectOn(id1);//修改的景点列表
        String scen = res.getScenic().getName();//景点名称
        Integer id = res.getScenic().getId();//景点ID
        List<Scenic> se = scenicService.SeleteAll();//所在景区下拉列表展示需要
        String imagess = res.getSpotImages();//上传配图的保存其路径和图片名称.及大小
        String[] split = imagess.split(",");//得到每个图片的信息
        int i = split.length;//图片的个数
        String I = i + "个文件";//前端显示图片的个数
        double K = 0.0;
        Map<String, String> map = new HashMap<>();//把分离出的每个图片信息添加到map，返回给修改页显示
        for (int a = 0; a < split.length; a++) {
            String[] split1 = split[a].split(":"); //得道路径的大小
            String urlname = split1[0].substring(1, split1[0].length() - 1);
            String ksize = split1[1].substring(1, split1[1].length() - 1);//图片大小
            String si = ksize.substring(0, ksize.length() - 1);
            double size = Double.parseDouble(si);
            String[] uns = urlname.split("&");//分离出图片路径和名字
            String urln = uns[0];//图片路径
            String name = uns[1];//图片名称
            K = K + size;
            String arr ="/"+ urln + "/" + name;
            map.put(arr, size + "kb" + "&" + name);//把图片名称和大小保存到map中
        }
        String s = String.valueOf(K);
        int i1 = s.indexOf(".");
        String substring = s.substring(0, i1 + 3);//保留总数的小数后两位
        String ss = "总计：" + substring + "kb";//前端显示已上传的文件大小

        //获取缩虐图
        String thu = res.getThumbnail();
        model.addAttribute("RNS", res);
        model.addAttribute("scenics", se);
        model.addAttribute("map", map);//配图表格
        model.addAttribute("id1", id);
        model.addAttribute("eid", id1);//当前修改页ID
        model.addAttribute("id", res.getId());
        model.addAttribute("scen", scen);
        model.addAttribute("str", imagess);//已上传的图片信息
        model.addAttribute("I", I);//已上传文件个数
        model.addAttribute("K", ss);//已上传文件大小
        model.addAttribute("thu", thu);//缩虐图展示
        model.addAttribute("page", page);

        return "scennic_2";
    }

    //景点修改发布界面
    @PostMapping("update2")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String update3(@RequestParam("spotImages") String imgs, @RequestParam("yida2") MultipartFile file, @RequestParam("pages") String page, @RequestParam("id") Integer id1, ReleasingNotices res) throws Exception {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fo = sim.format(res.getModifyTime());
        Date pars = sim.parse(fo);
        res.setModifyTime(pars);
        String na = file.getOriginalFilename();
        if (na.length() > 3) {
            String Filename = addfile(file);
            String path = "/img/";
            String str = path + Filename; //组合c
            res.setThumbnail(str); //保存到数据库
        }
        res.setSpotImages(imgs);
        res.setId(id1);
        releasingNoticesService.Update(res);
        return "redirect:/main/scennic?page=" + page;
    }

    //删除单个页面  景点列表
    @GetMapping("delete")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String deleteone(@RequestParam("id") Integer id, @RequestParam("page") Integer page) {
        releasingNoticesService.delete(id);
        return "redirect:/main/scennic?page=" + page;
    }

    //景点列表多个删除界面
    @GetMapping("/delete2")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String deleteall(@RequestParam("ids") String ids) {
        String s = ids.substring(0, ids.length() - 1);
        String[] split = s.split(",");
        Integer[] arr = new Integer[split.length];
        for (int a = 0; a < split.length; a++) {
            arr[a] = Integer.parseInt(split[a]);
        }
        for (int i = 0; i < arr.length; i++) {
            releasingNoticesService.delete(arr[i]);
        }
        return "redirect:/main/scennic";
    }


    @GetMapping("messages_4")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public  String addmessg(){
        return"message_3";
    }

    //焦点消息界面
    @GetMapping("/message")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String message(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        PageHelper.startPage(page, 2);
        List<Message> meselect = messageService.meselect(1);
        PageInfo<Message> pageinfo = new PageInfo<>(meselect);
        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages > 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }

        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("list", meselect);
        return "message";
    }

    //焦点消息发布提交界面
    @PostMapping("/message_5")
    @RequiresRoles(value = {"admin"})
    public String message_5(@RequestParam("yida") MultipartFile file, Message me) throws Exception {

        me.setModifyTime(new Date());
        addfile(file);
        String Filename = file.getOriginalFilename();// 获取上传文件的名称
        String path = "/img" + File.separator;
        String str = path + Filename; //组合c
        me.setThumbnail(str); //保存到数据库
        me.setFocus(1);
        System.out.println(me);
        messageService.add(me);
        return "redirect:/main/message";
    }


    //焦点消息修改界面
    @GetMapping("/updatemea")
    @RequiresRoles(value = {"admin"})
    public String meupdate2(@RequestParam("id") Integer id, Model model) {
        Message mes = messageService.selecton(id);
        model.addAttribute("message", mes);
        return "message_4";
    }

    //焦点消息修改提交界面
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @PostMapping("/upmessage_3")
    public String message_4(@RequestParam("yida") MultipartFile file, @RequestParam("id") Integer id, Message me) throws Exception {
        addfile(file);
        String Filename = file.getOriginalFilename();// 获取上传文件的名称
        String path = "/img" + File.separator;
        String str = path + Filename; //组合c
        me.setThumbnail(str); //保存到数据库
        me.setId(id);
        messageService.updateon(me);
        return "redirect:/main/message";
    }

    //焦点消息搜索按钮
    @GetMapping("mes1")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String mest(@RequestParam("title") String ByName, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        String name = "%" + ByName.trim() + "%";
        //分页
        PageHelper.startPage(page, 2);
        List<Message> emp = messageService.selectallto(name, begin, end);
        PageInfo<Message> pageinfo = new PageInfo<>(emp);
        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages >= 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("list", emp);
        model.addAttribute("nameby", ByName);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        return "message1.1";
    }

    //焦点消息搜索按钮
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    @PostMapping("mes1")
    public String mes(@RequestParam("title") String ByName, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        String name = "%" + ByName.trim() + "%";
        //分页
        PageHelper.startPage(page, 2);
        List<Message> emp = messageService.selectallto(name, begin, end);
        PageInfo<Message> pageinfo = new PageInfo<>(emp);
        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages >= 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }

        }

        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("list", emp);
        model.addAttribute("nameby", ByName);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        return "message1.1";
    }

    //消息列表搜索按钮
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    @RequestMapping(value = "mes2", method = RequestMethod.POST)
    public String mess(@RequestParam("title") String ByName, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        String name = "%" + ByName.trim() + "%";
        //分页
        PageHelper.startPage(page, 2);
        List<Message> emp = messageService.selectallto2(name, begin, end);
        PageInfo<Message> pageinfo = new PageInfo<>(emp);
        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages >= 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("list", emp);
        model.addAttribute("nameby", ByName);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        return "message2.2";
    }

    @GetMapping("/mes2")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String messq(@RequestParam("title") String ByName, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        String name = "%" + ByName.trim() + "%";
        //分页
        PageHelper.startPage(page, 2);
        List<Message> emp = messageService.selectallto2(name, begin, end);
        PageInfo<Message> pageinfo = new PageInfo<>(emp);
        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages >= 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("list", emp);
        model.addAttribute("nameby", ByName);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        return "message2.2";
    }

    //焦点消息单个删除按钮
    @GetMapping("deletemea")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String medeleteone2(@RequestParam("id") Integer id) {
        messageService.delete(id);
        return "redirect:/main/message";
    }

    //焦点消息多个删除界面
    @GetMapping("/medelete3")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String deletealls2(@RequestParam("ids") String ids) {
        String s = ids.substring(0, ids.length() - 1);
        String[] split = s.split(",");
        Integer[] arr = new Integer[split.length];
        for (int a = 0; a < split.length; a++) {
            arr[a] = Integer.parseInt(split[a]);
        }
        for (int i = 0; i < arr.length; i++) {
            messageService.delete(arr[i]);
        }
        return "redirect:/main/message";
    }

    //消息列表主界面
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    @RequestMapping(value = "/message2", method = {RequestMethod.POST, RequestMethod.GET})
    public String message2(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        PageHelper.startPage(page, 2);
        List<Message> list = messageService.selectall();
        PageInfo<Message> pageinfo = new PageInfo<>(list);
        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages >= 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }

        }

        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("page", page);


        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("list", list);

        model.addAttribute("port", port);
        return "message2";
    }

    @GetMapping("/message_1") //消息发布界面
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String message_1() {
        return "message_1";
    }

    @PostMapping("/message_2")//消息发布提交界面
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String message_2(@RequestParam("yida") MultipartFile file, Message me) throws Exception {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fo = sim.format(me.getModifyTime());
        Date pars = sim.parse(fo);
        me.setModifyTime(pars);
        addfile(file);
        String Filename = file.getOriginalFilename();// 获取上传文件的名称
        String path = "/img" + File.separator;
        String str = path + Filename; //组合c
        me.setThumbnail(str); //保存到数据库
        me.setFocus(0);
        System.out.println(me);
        messageService.add(me);
        return "redirect:/main/message2";
    }

    //消息列表单选焦点按钮
    @GetMapping("/focue")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String focue(@RequestParam("focue") Integer focue, @RequestParam("id") Integer id) {
        Message me = messageService.selecton(id);
        me.setFocus(focue);
        messageService.updateon(me);
        return "redirect:/main/message2";
    }

    //消息列表多选焦点按钮
    @GetMapping("/focues")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String focues(@RequestParam("ids") String focues) {
        String s = focues.substring(0, focues.length() - 1);
        String[] split = s.split(",");
        for (int a = 0; a < split.length; a++) {
            int f = Integer.parseInt(split[a]);
            Message me = messageService.selecton(f);
            if (me.getFocus() == 0) {
                me.setFocus(1);
                messageService.updateon(me);
            } else if (me.getFocus() == 1) {
                me.setFocus(0);
                messageService.updateon(me);
            }
        }
        return "redirect:/mian/message2";
    }

    //消息列表多个删除界面
    @GetMapping("/medelete2")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String deletealls(@RequestParam("ids") String ids) {
        String s = ids.substring(0, ids.length() - 1);
        String[] split = s.split(",");
        Integer[] arr = new Integer[split.length];
        for (int a = 0; a < split.length; a++) {
            arr[a] = Integer.parseInt(split[a]);
        }
        for (int i = 0; i < arr.length; i++) {
            messageService.delete(arr[i]);
        }
        return "redirect:/main/message2";
    }

    //删除单个页面  景点列表
    @GetMapping("medelete")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String medeleteone(@RequestParam("id") Integer id) {
        messageService.delete(id);
        return "redirect:/main/message2";
    }

    //消息更改界面
    @GetMapping("/meupdate")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String meupdate(@RequestParam("id") Integer id, Model model) {
        Message mes = messageService.selecton(id);
        model.addAttribute("message", mes);
        return "message_2";
    }

    //更改消息提交界面
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @PostMapping("upmessage_2")
    public String meupdate(@RequestParam("yida") MultipartFile file, @RequestParam("id") Integer id, Message me) throws Exception {
        addfile(file);
        String Filename = file.getOriginalFilename();// 获取上传文件的名称
        String path = "/img" + File.separator;
        String str = path + Filename; //组合c
        me.setThumbnail(str); //保存到数据库
        me.setId(id);
        messageService.updateon(me);
        return "redirect:/main/message2";
    }

    //周边列表主界面
    @GetMapping("/rim")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String rim(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        List<Classify> list = classifyService.selectall();
        PageHelper.startPage(page, 2);
        List<Peripheral> list2 = peripheralService.selectall();
        PageInfo<Peripheral> pageinfo = new PageInfo<>(list2);
        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages >= 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }

        }

        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("list", list);
        model.addAttribute("perh", list2);
        return "rim";
    }

    //周边列表添加界面
    @GetMapping("/rim1")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String rim_1(Model model) {
        List<Classify> list = classifyService.selectall();
        model.addAttribute("list", list);
        return "rim_1";
    }

    //周边发布界面
    @PostMapping("/rimp")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String rim_2(@RequestParam("spotImages") String spotImages, @RequestParam("yida2") MultipartFile file, Peripheral p) throws Exception {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fo = sim.format(p.getModifyTime());
        Date pars = sim.parse(fo);
        p.setModifyTime(pars);

        String Filename = addfile(file);
        String path = "/img" + File.separator;
        String str = path + Filename; //组合c
        p.setThumbnail(str); //保存到数据库
        p.setSpotImages(spotImages);
        peripheralService.add(p);
        return "redirect:/main/rim";
    }

    //周边修改页面
    @GetMapping("/rimup")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String rim_3(@RequestParam("id") Integer id, @RequestParam(value = "page") Integer page, Model model) {
        Peripheral perh = peripheralService.selecton(id);
        List<Classify> list = classifyService.selectall();
        String scen = perh.getClassify().getName();//景点名称
        Integer id1 = perh.getClassify().getId();//景点ID
        String images = perh.getSpotImages();//上传配图的保存其路径和图片名称.及大小
        String[] split = images.split(",");//得到每个图片的信息
        int i = split.length;//图片的个数
        String I = i + "个文件";//前端显示图片的个数
        double K = 0.0;
        Map<String, String> map = new HashMap<>();//把分离出的每个图片信息添加到map，返回给修改页显示
        for (int a = 0; a < split.length; a++) {
            String[] split1 = split[a].split(":"); //得道路径的大小
            String urlname = split1[0].substring(1, split1[0].length() - 1);
            String ksize = split1[1].substring(1, split1[1].length() - 1);//图片大小
            String si = ksize.substring(0, ksize.length() - 1);
            double size = Double.parseDouble(si);
            String[] uns = urlname.split("&");//分离出图片路径和名字
            String urln = uns[0];//图片路径
            String name = uns[1];//图片名称
            K = K + size;
            String arr =File.separator+  urln + File.separator + name;
            map.put(arr, size + "kb" + "&" + name);//把图片名称和大小保存到map中
        }
        String s = String.valueOf(K);
        int i1 = s.indexOf(".");
        String substring = s.substring(0, i1 + 3);//保留总数的小数后两位
        String ss = "总计：" + substring + "kb";//前端显示已上传的文件大小
        //获取缩虐图
        String thu = perh.getThumbnail();
        model.addAttribute("str", images);//已上传的图片信息
        model.addAttribute("I", I);//已上传文件个数
        model.addAttribute("K", ss);//已上传文件大小
        model.addAttribute("thu", thu);//缩虐图展示
        model.addAttribute("map", map);//配图表格
        model.addAttribute("id", id1);
        model.addAttribute("eid", id);
        model.addAttribute("scen", scen);
        model.addAttribute("list", list);
        model.addAttribute("perh", perh);
        model.addAttribute("page", page);
        return "rim_2";
    }

    //周边修改提交界面
    @PostMapping("rimp22")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String rimm(@RequestParam("spotImages") String spotImages, @RequestParam("yida2") MultipartFile file, @RequestParam("id") Integer id1, @RequestParam(value = "page") Integer page, Peripheral p) throws Exception {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fo = sim.format(p.getModifyTime());
        Date pars = sim.parse(fo);
        p.setModifyTime(pars);

        String na = file.getOriginalFilename();
        if (na.length() > 3) {
            String Filename = addfile(file);
            String path = "/img" + File.separator;
            String str = path + Filename; //组合c
            p.setThumbnail(str);
        }
        p.setSpotImages(spotImages);
        p.setId(id1);
        peripheralService.Update(p);
        return "redirect:/main/rim?page=" + page;
    }

    //周边删除多个界面
    @GetMapping("/rim3")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String rim_3(@RequestParam("ids") String ids) {
        String s = ids.substring(0, ids.length() - 1);
        String[] split = s.split(",");
        Integer[] arr = new Integer[split.length];
        for (int a = 0; a < split.length; a++) {
            arr[a] = Integer.parseInt(split[a]);
        }
        for (int i = 0; i < arr.length; i++) {
            peripheralService.deleteon(arr[i]);
        }

        return "redirect:/main/rim";
    }

    //周边单个删除
    @GetMapping("rimdel")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String rim_4(@RequestParam("id") Integer id, @RequestParam(value = "page") Integer page) {
        peripheralService.deleteon(id);
        return "redirect:/main/rim?page=" + page;
    }

    //周边列表搜索按钮
    @PostMapping("rimt")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String rims(@RequestParam("title") String ByName, @RequestParam("classify.id") Integer classify_id, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        List<Classify> sec = classifyService.selectall();
        Classify clasify = classifyService.readon(classify_id);
        String name = "%" + ByName.trim() + "%";
        //分页
        PageHelper.startPage(page, 2);
        List<Peripheral> emp = peripheralService.selectall(name, classify_id, begin, end);
        PageInfo<Peripheral> pageinfo = new PageInfo<>(emp);

        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages > 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }

        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("page", page);
        model.addAttribute("perh", emp);
        model.addAttribute("nameby", ByName);
        model.addAttribute("list", sec);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        if (clasify != null) {
            model.addAttribute("scen", clasify.getName());
        }
        model.addAttribute("id", classify_id);
        return "rim1";
    }

    //周边列表分页搜索按钮
    @GetMapping("rimt")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String rims2(@RequestParam("title") String ByName, @RequestParam("classify.id") Integer classify_id, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        List<Classify> sec = classifyService.selectall();
        Classify clasify = classifyService.readon(classify_id);
        String name = "%" + ByName.trim() + "%";
        //分页
        PageHelper.startPage(page, 2);
        List<Peripheral> emp = peripheralService.selectall(name, classify_id, begin, end);
        PageInfo<Peripheral> pageinfo = new PageInfo<>(emp);

        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages > 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }

        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("page", page);
        model.addAttribute("perh", emp);
        model.addAttribute("nameby", ByName);
        model.addAttribute("list", sec);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        if (clasify != null) {
            model.addAttribute("scen", clasify.getName());
        }
        model.addAttribute("id", classify_id);
        return "rim1";
    }

    //视频列表界面
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    @GetMapping("/video")
    public String video(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        List<Scenic> scenics = scenicService.SeleteAll();
        PageHelper.startPage(page, 1);
        List<Video> list = videoService.selectall();
        PageInfo<Video> pageinfo = new PageInfo<>(list);
        model.addAttribute("scenics", scenics);
        model.addAttribute("list", list);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("page", page);

        return "video";
    }

    //视频列表搜索按钮
    @PostMapping("liakall")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String messp(@RequestParam("title") String ByName, @RequestParam("scenic.id") Integer scenic_id, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        List<Scenic> sec = scenicService.SeleteAll();
        Scenic selecton = scenicService.selecton(scenic_id);
        String name = "%" + ByName.trim() + "%";
        //分页
        PageHelper.startPage(page, 1);
        List<Video> emp = videoService.Readrn(name, scenic_id, begin, end);
        PageInfo<Video> pageinfo = new PageInfo<>(emp);

        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages > 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }

        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("page", page);
        model.addAttribute("list", emp);
        model.addAttribute("nameby", ByName);
        model.addAttribute("scenics", sec);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        if (selecton != null) {
            model.addAttribute("scen", selecton.getName());
        }
        model.addAttribute("id", scenic_id);
        return "video2";
    }

    //视频列表搜索按钮
    @GetMapping("liakall2")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String messp2(@RequestParam("title") String ByName, @RequestParam("scenic.id") Integer scenic_id, @RequestParam("begin") String begin, @RequestParam("end") String end, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        List<Scenic> sec = scenicService.SeleteAll();
        Scenic selecton = scenicService.selecton(scenic_id);
        String name = "%" + ByName.trim() + "%";

        //分页
        PageHelper.startPage(page, 1);
        List<Video> emp = videoService.Readrn(name, scenic_id, begin, end);
        PageInfo<Video> pageinfo = new PageInfo<>(emp);

        int pages = pageinfo.getPages();//总页数
        int[] arr = new int[5];
        System.out.println(arr.length);
        int s = 0;
        if (pages > 6) {
            if (page >= 4 && page > pages - 3 == false) {
                for (int i = page - 2; i < page + 3; i++) {
                    arr[s] = i;
                    s++;
                }
            }
            if (page > pages - 3) {
                for (int i = pages - 4; i <= pages; i++) {
                    arr[s] = i;
                    s++;
                }
            }
        }
        if (s != 0) {
            model.addAttribute("p1", arr[0]);
            model.addAttribute("p2", arr[1]);
            model.addAttribute("p3", arr[2]);
            model.addAttribute("p4", arr[3]);
            model.addAttribute("p5", arr[4]);
        }
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("page", page);
        model.addAttribute("list", emp);
        model.addAttribute("nameby", ByName.trim());
        model.addAttribute("scenics", sec);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        if (selecton != null) {
            model.addAttribute("scen", selecton.getName());
        }
        model.addAttribute("id", scenic_id);
        return "video2";
    }

    @GetMapping("/video_1")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String video1(Model model) {
        List<Scenic> scenics = scenicService.SeleteAll();
        model.addAttribute("scenics", scenics);
        return "video_1";
    }

    @GetMapping("/video2")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String video0(Model model) {
        List<Scenic> scenics = scenicService.SeleteAll();
        model.addAttribute("scenics", scenics);
        return "video2";
    }

    //视频列表添加界面
    @PostMapping("postvoide")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String vo(@RequestParam("spotImages") String spotImages, @RequestParam("yida2") MultipartFile file, Video v) throws Exception {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fo = sim.format(v.getModifyTime());
        Date pars = sim.parse(fo);
        v.setModifyTime(pars);

        String Filename = addfile(file);
        String path = "/img" + File.separator;
        String str = path + Filename; //组合c
        v.setThumbnail(str); //保存到数据库
        v.setSpotImages(spotImages);
        videoService.addon(v);
        return "redirect:/mian/video";
    }

    //视频修改界面
    @GetMapping("updatevo")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String ve(@RequestParam("id") Integer id, @RequestParam("page") Integer page, Model model) {
        List<Scenic> scenics = scenicService.SeleteAll();

        Video video = videoService.selecton(id);
        String spo = video.getSpotImages();//获取视频的数据库信息
        String name = video.getScenic().getName();
        Integer cid = video.getScenic().getId();
        Map<String, String> map = new HashMap<>();
        double sk = 0;
        int N = 0;
        if (spo.split(":").length > 2) {//说明不止一个视频文件需要对数据库信息景区分离展示
            String[] split = spo.split(",");
            for (int a = 0; a < split.length; a++) {
                String sp1 = split[a];
                String[] split1 = sp1.split(":");
                String sizes = split1[1].substring(1, split1[1].length() - 1);
                String names = split1[0].substring(1, split1[0].length() - 1);
                String[] split2 = names.split("&");
                String na = split2[1];
                map.put(na, sizes);
                N = split.length;
                sk = sk + Double.parseDouble(sizes.substring(0, sizes.length() - 2));
            }
        } else {
            String[] arr = spo.split(":");
            String siz = arr[1].substring(1, arr[1].length() - 1);
            String sub = arr[0].substring(1, arr[0].length() - 1);
            String[] arrys = sub.split("&");
            String nam = arrys[1];
            map.put(nam, siz);
            N = 1;
            sk = Double.parseDouble(siz.substring(0, siz.length() - 2));
        }
        model.addAttribute("scenics", scenics);//下拉列表展示需要
        model.addAttribute("vo", video);//修改的对象
        model.addAttribute("spot", spo);//视频文件的数据库信息
        model.addAttribute("cname", name);//分类下拉默认选中的分类信息
        model.addAttribute("cid", cid);//对应的value
        model.addAttribute("map", map);//对应的value
        model.addAttribute("N", N);//对应的value
        model.addAttribute("sk", sk);//对应的value
        model.addAttribute("page", page);//对应的value
        return "video_2";
    }

    @GetMapping("video_2")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String vid() {
        return "video_2";
    }

    //视频列表修改提交页
    @PostMapping("updatevideo")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String tivideo(@RequestParam("spotImages") String spotImages, @RequestParam("yida2") MultipartFile file, @RequestParam("page") String page, @RequestParam("uid") Integer uid, Video v) throws Exception {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fo = sim.format(v.getModifyTime());
        Date pars = sim.parse(fo);
        v.setModifyTime(pars);
        String na = file.getOriginalFilename();
        if (na.length() > 3) {
            String Filename = addfile(file);
            String path = "/img" + File.separator;
            String str = path + Filename; //组合c
            v.setThumbnail(str);
        }
        v.setSpotImages(spotImages);
        v.setId(uid);
        videoService.updateon(v);
        return "redirect:/main/video?page=" + page;
    }

    //视频单删除按钮
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @GetMapping("/videodelete")
    public String serve_0(@RequestParam("id") Integer id, @RequestParam("page") Integer page) {
        videoService.deleteon(id);
        return "redirect:/main/video?page=" + page;
    }

    //视频多个删除界面
    @GetMapping("/videodu")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String deletealls0(@RequestParam("ids") String ids, @RequestParam("page") Integer page) {
        String s = ids.substring(0, ids.length() - 1);
        String[] split = s.split(",");
        Integer[] arr = new Integer[split.length];
        for (int a = 0; a < split.length; a++) {
            arr[a] = Integer.parseInt(split[a]);
        }
        for (int i = 0; i < arr.length; i++) {
            videoService.deleteon(arr[i]);
        }
        return "redirect:/main/video?page=" + page;
    }


    @GetMapping("/serve")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String serve(Model model) {
        List<Serve> list = serveService.selestall();
        model.addAttribute("list", list);
        return "serve";
    }

    //添加景区咨询界面
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @GetMapping("/serve_1")
    public String serve1() {
        return "serve_1";
    }

    //景区咨询提交界面
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @PostMapping("/serve1")
    public String serve_1(Serve s) {
        serveService.add(s);
        return "redirect:/main/serve";
    }

    //景区咨询单删除按钮
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @GetMapping("/serve_2")
    public String serve_2(@RequestParam("id") Integer id) {
        serveService.deleteon(id);
        return "redirect:/main/serve";
    }

    //景区咨询多个删除界面
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    @GetMapping("/deleteser")
    public String deletealls3(@RequestParam("ids") String ids) {
        String s = ids.substring(0, ids.length() - 1);
        String[] split = s.split(",");
        Integer[] arr = new Integer[split.length];
        for (int a = 0; a < split.length; a++) {
            arr[a] = Integer.parseInt(split[a]);
        }
        for (int i = 0; i < arr.length; i++) {
            serveService.deleteon(arr[i]);
        }
        return "redirect:/main/serve";
    }

    //投诉和建议主显示界面
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    @GetMapping("/serve2")
    public String serve2(Model model) {
        List<Servetwo> list = servetwoService.selectall();
        System.out.println(list);
        model.addAttribute("list", list);
        return "serve2";

    }

    @PostMapping("/serve2")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String serves2(Model model) {
        List<Servetwo> list = servetwoService.selectall();
        System.out.println(list);
        model.addAttribute("list", list);
        return "serve2";

    }


    //投诉和建议多个删除
    @GetMapping("serve2_1")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String serve2_1(@RequestParam("ids") String ids) {
        String s = ids.substring(0, ids.length() - 1);
        String[] split = s.split(",");
        Integer[] arr = new Integer[split.length];
        for (int a = 0; a < split.length; a++) {
            arr[a] = Integer.parseInt(split[a]);
        }
        for (int i = 0; i < arr.length; i++) {
            servetwoService.deleteon(arr[i]);
        }
        return "redirect:/main/serve2";
    }

    //投诉和建议单个删除
    @GetMapping("/ser2")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String serve_2_1(@RequestParam("id") Integer id) {
        servetwoService.deleteon(id);
        return "redirect:/main/serve2";
    }


    @GetMapping("/serve3")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String serve3(Model model) {
        Serverthree the = serverthreeService.selectin(3);
        model.addAttribute("the", the);
        return "serve3";
    }

    //交通与信息提交界面
    @PostMapping("ser3")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String serve5(Serverthree s, Model model) {
        serverthreeService.add(s);
        model.addAttribute("the", s);
        return "serve3";
    }

    //长寿概况
    @GetMapping("/setup")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String setup(Model model) {
        SetupTwo two = setupTwoService.selecton(1);
        String imgs = two.getImgs();
        System.out.println(imgs);
        String[] split = imgs.split(",");//得到每个图片的信息
        int i = split.length;//图片的个数
        String I = i + "个文件";//前端显示图片的个数
        double K = 0.0;
        Map<String, String> map = new HashMap<>();//把分离出的每个图片信息添加到map，返回给修改页显示
        for (int a = 0; a < split.length; a++) {
            String[] split1 = split[a].split(":"); //得道路径的大小
            String urlname = split1[0].substring(1, split1[0].length() - 1);
            String ksize = split1[1].substring(1, split1[1].length() - 1);//图片大小
            String si = ksize.substring(0, ksize.length() - 1);
            double size = Double.parseDouble(si);
            String[] uns = urlname.split("&");//分离出图片路径和名字
            String urln = uns[0];//图片路径
            String name = uns[1];//图片名称
            K = K + size;
            String arr = File.separator+urln + File.separator + name;
            map.put(arr, size + "kb" + "&" + name);//把图片名称和大小保存到map中
        }
        String s = String.valueOf(K);
        int i1 = s.indexOf(".");
        String substring = s.substring(0, i1 + 3);//保留总数的小数后两位
        String ss = "总计：" + substring + "kb";//前端显示已上传的文件大小

        model.addAttribute("setup", two);
        model.addAttribute("str", imgs);//配图数据库信息
        model.addAttribute("id", 1);//修改的数据ID
        model.addAttribute("I", I);//已上传文件个数
        model.addAttribute("K", ss);//已上传文件大小
        model.addAttribute("map", map);//已上传文件大小
        return "setup";
    }

    //长寿概况修改页
    @PostMapping("setup1")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String seup(@RequestParam("spotImages") String imgs, @RequestParam("cid") Integer id, SetupTwo s) {
        s.setImgs(imgs);
        s.setId(id);
        setupTwoService.updateon(s);
        return "redirect:/main/setup";
    }


    //版本管理
    @GetMapping("/setup2")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String setup2(Model model) {
        Setup sup = setupService.selecton(1);
        sup.setAndtype("Iphone");
        model.addAttribute("sup", sup);
        return "setup2";
    }

    @GetMapping("/setup3")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)//有一个符合就通过
    @RequiresPermissions(value = {"admin:select:*"})//符合当前权限字符串的角色才能访问
    public String setup4(String cid, Model model) {
        int c = Integer.parseInt(cid);
        Setup sup = setupService.selecton(c);
        int ss = Integer.parseInt(sup.getAndtype());
        if (ss == 1) {
            sup.setAndtype("Iphone");
        } else if (ss == 0) {
            sup.setAndtype("Android");
        }
        model.addAttribute("sup", sup);
        return "setup2";
    }


    @PostMapping("/setup2")
    @RequiresRoles(value = {"admin"})//有一个符合就通过
    public String setup3(Setup s, Model model) {
        int s1 = Integer.parseInt(s.getAndtype());
        if (s1 == 1) {
            s.setId(1);
        } else if (s1 == 0) {
            s.setId(2);
        }
        setupService.updateon(s);

        return "redirect:/main/setup3?cid=" + s.getId();
    }


    public String addfile(MultipartFile file) throws Exception {
        String Filename2 = file.getOriginalFilename();// 获取上传文件的名称
        if (!file.isEmpty()) {
            String Filename = file.getOriginalFilename();// 获取上传文件的名称
            String type = file.getContentType();// 获取文件的类型
            // 限定文件的类型为jpg，png格式。若不符合抛出异常
            if (type.endsWith("jpg") || type.endsWith("png") || type.endsWith("jpeg")) {
                String size = getFileSize(file.getSize());// 获取文件大小 kb M
                //文件上传目录    保存到public、img下
                String folder = uploadFolder + "/img/";
                File uploadDir = new File(folder);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                //文件上传
                File file2 = new File(uploadDir.getAbsolutePath(), Filename);//保存到public下的img下
                file.transferTo(file2);
            }
        }
        return Filename2;
    }

    // 计算文件大小
    public String getFileSize(long size) {
        // 1G=1024M, 1M=1024K, 1K=1024B
        String strSize = null;
        if (size >= 1024 * 1024 * 1024) {// G
            // %表示语法，必须写，.3表示小数点后保留3位有效数字，f表示格式化浮点数，G只是随便写的。
            strSize = String.format("%.3fG", size / (1024 * 1024 * 1024.0));
        } else if (size >= 1024 * 1024) {// M
            strSize = String.format("%.3fM", size / (1024 * 1024.0));
        } else if (size >= 1024) {// K
            strSize = String.format("%.3fK", size / 1024.0);
        } else {// B
            strSize = size + "B";
        }
        return strSize;
    }

    public String getFileName2(String name) {
        int code = name.hashCode();// 获取hashCode码
        int first = code & 0XF;// 第一层目录
        int second = code & (0XF >> 1);// 第二层目录
        String str1 = UUID.randomUUID().toString();// 随机字符串
        String str2 = name.substring(name.indexOf("."));// 文件后辍
        String str = first + "/" + second + "/" + str1 + str2;
        return str;
    }



 /*   @ResponseBody
    @RequestMapping(value = "/getAjaxUploadFiles", method = RequestMethod.POST)
    public String imger(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                System.out.println(file.getName());
                if (!file.isEmpty()) {
                    String name = file.getName();// 获取文件上传组建的名称即：<input type="File" name="yida"> name的属性yida
                    String Filename = file.getOriginalFilename();// 获取上传文件的名称
                    String type = file.getContentType();// 获取文件的类型
                    // 限定文件的类型为jpg，png格式。若不符合抛出异常
                    if (type.endsWith("jpg") || type.endsWith("png") || type.endsWith("jpeg")) {
                        String size = getFileSize(file.getSize());// 获取文件大小 kb M
                        System.out.println(size);
                        String realPath = request.getServletContext().getRealPath("/");
                        String ss = getFileName2(Filename);
                        File f = new File(realPath + ss, Filename);

                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        resultMap.put(ss + '&' + Filename, size);
                        file.transferTo(f);
                    }
                }
            }
            return mapToJson(resultMap);
        }
        resultMap.put("code", 401);//封装回调信息
        resultMap.put("msg", "上传失败");
        return mapToJson(resultMap);

    }
*/


    }
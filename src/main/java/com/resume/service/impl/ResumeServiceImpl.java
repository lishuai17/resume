package com.resume.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.dao.ResumeInfoDao;
import com.resume.dto.ResumeFile;
import com.resume.dto.ResumeInfo;
import com.resume.enums.FileType;
import com.resume.po.ResumeInfoPo;
import com.resume.service.ResumeFileService;
import com.resume.service.ResumeService;
import com.resume.util.BeanUtil;

@Service
public class ResumeServiceImpl implements ResumeService {
	
	@Autowired
	private ResumeInfoDao resumeInfoDao;
	
	@Autowired
	private ResumeFileService resumeFileService;

	@Override
	public ResumeInfo getResumeById(long id) {
		ResumeInfoPo resumeInfoPo = resumeInfoDao.queryById(id);
		return BeanUtil.createCopy(resumeInfoPo, ResumeInfo.class);
	}

	@Override
	public long saveResumeInfo(ResumeInfo resumeInfo) {
		
		ResumeInfoPo resumeInfoPo = BeanUtil.createCopy(resumeInfo, ResumeInfoPo.class);
		resumeInfoDao.insertResumeInfo(resumeInfoPo);
		
		return resumeInfoPo.getId();
	}

	@Override
	public void updateResumeInfo(ResumeInfo resumeInfo) {
		if(null != resumeInfo){
			resumeInfoDao.updateResumeInfo(BeanUtil.createCopy(resumeInfo, ResumeInfoPo.class));
		}

	}

	@Override
	public void deleteResumeInfo(long id) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public List<ResumeInfo> getResumes(int page, int size) {
		int beginIndex = (page - 1) * size;
		
		List<ResumeInfoPo> listPo = resumeInfoDao.queryList(beginIndex, size);
		List<Long> resumeIds = new ArrayList<Long>();
		for (ResumeInfoPo resumeInfoPo : listPo) {
			resumeIds.add(resumeInfoPo.getId());
		}
		
		List<ResumeFile> files = resumeFileService.getResumeFileByResumeIdsAndType(resumeIds, FileType.RESUME_DOC.getCode());
		Map<Long, ResumeFile> map = new HashMap<Long, ResumeFile>();
		for (ResumeFile resumeFile : files) {
			map.put(resumeFile.getResumeId(), resumeFile);
		}
		
		List<ResumeInfo> resumes = new ArrayList<ResumeInfo>();
		for (ResumeInfoPo resumeInfoPo : listPo) {
			ResumeInfo resume = BeanUtil.createCopy(resumeInfoPo, ResumeInfo.class);
			resume.setResumeFile(map.get(resumeInfoPo.getId()));
			resumes.add(resume);
		}
		
		return resumes;
	}

}